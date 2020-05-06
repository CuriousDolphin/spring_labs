package it.polito.ai.lab3.services;

import it.polito.ai.lab3.controllers.NotificationController;
import it.polito.ai.lab3.dtos.TeamDTO;
import it.polito.ai.lab3.entities.Token;
import it.polito.ai.lab3.exceptions.TokenNotFoundException;
import it.polito.ai.lab3.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    TeamService teamService;

    @Autowired
    TokenRepository tokenRepository;


    public void sendMessage(String address, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("s263138@studenti.polito.it");
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);

    }


    private Long checkToken(String token){ // controlla la validita del token, se valido lo elimina e ritorna il team id
        Optional<Token> t=tokenRepository.findById(token);
        if(t.isEmpty()) {
            System.out.println("TOKEN NON ESISTE");
            throw  new TokenNotFoundException();
        }
        System.out.println("SCADENZA TOKEN "+t.get().getExpiryDate().toString());
        if(t.get().getExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
            System.out.println("TOKEN SCADUTO");
            throw  new TokenNotFoundException();
        }
        Long teamId=t.get().getTeamId();
        tokenRepository.delete(t.get()); // elimino il token
        return teamId;

    }

    @Override
    public boolean confirm(String token)
    {


        Long teamId=checkToken(token);
        if (teamId == null) return false;

        if(tokenRepository.findAllByTeamId(teamId).size() > 0) {
            System.out.println("Ci sono ancora token pendenti =(");
            return false;
        }

        teamService.activateTeam(teamId);
        return true;
    }

    @Override
    public boolean reject(String token) {
        Long teamId=checkToken(token);
        if (teamId == null) return false;

        List<Token> list=tokenRepository.findAllByTeamId(teamId);

        list.forEach(t -> tokenRepository.delete(t));
        teamService.evictTeam(teamId);


        return true;
    }

    @Override
    @Async
    public void notifyTeam(TeamDTO dto, List<String> memberIds) {
        long now = System.currentTimeMillis();
        memberIds.forEach(id ->{
            Token t = new Token();
            t.setId(UUID.randomUUID().toString());
            t.setExpiryDate(new Timestamp(now +3600000));
            t.setTeamId(dto.getId());

            tokenRepository.save(t);

            String confirmLink = linkTo(NotificationController.class).slash("confirm").slash(t.getId()).toString();
            String rejectLink = linkTo(NotificationController.class).slash("reject").slash(t.getId()).toString();
            String subject="You have invited to join group "+dto.getName();
            String text ="confirm:  "+confirmLink+"\n reject: "+rejectLink;

            sendMessage('s'+id+"@studenti.polito.it",subject,text);

        });

    }
}
