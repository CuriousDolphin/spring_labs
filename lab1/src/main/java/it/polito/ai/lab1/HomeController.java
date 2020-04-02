package it.polito.ai.lab1;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.stream.Stream;

@Log(topic = "HomeController")
@Controller
public class HomeController {
    RegistrationManager registrationManager;

    @Autowired
    public HomeController(RegistrationManager registrationManager) {
        this.registrationManager = registrationManager;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }


    @GetMapping("/login")
    public String loginPage(@ModelAttribute("command") LoginCommand command,HttpSession session) {
        log.info("-------> richiesta pagina login " + command.toString());
        if(session.getAttribute("username")!= null){
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("command") LoginCommand command,
            BindingResult bindingResult,
            HttpSession session
    ) {
        log.info("-------> richiesto login " + command.toString());
        if(session.getAttribute("username")!= null){
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            log.info("----- errori nel form di login");
            bindingResult.getAllErrors().forEach(error -> log.info("---" + error.getDefaultMessage()));
            return "login";
        }

        if (!this.registrationManager.containsKey(command.name)) {
            log.info("----- user " + command.name + " not exist");
            bindingResult.addError(new ObjectError("error","user not exist"));
            return "login";
        }
        RegistrationDetails rd = this.registrationManager.get(command.name);
        if (command.getPassword().equals(rd.getPassword1())) { // password uguali -> next fare l hash
            log.info("----- user " + command.name + " LOGGED");
            session.setAttribute("username",command.name);
            return "redirect:/private";
        } else {
            log.info("----- user " + command.name + " WRONG PASSWORD INSERT");
            bindingResult.addError(new ObjectError("error","access not allowed"));
            return "login";
        }


    }

    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("command") RegistrationCommand command,HttpSession session) {
        if(session.getAttribute("username")!= null){
            return "redirect:/";
        }
        log.info("-------> richiesta pagina registrazione " + command.toString());

        return "register.html";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("command") RegistrationCommand command,
            BindingResult bindingResult
    ) {
        log.info("-------> richiesta registrazione " + command.toString());
        if (!command.getPassword1().equals(command.getPassword2())) {
            bindingResult.addError(new FieldError("samePassword", "password2", "The passwords must be the same"));
        }
        if (bindingResult.hasErrors()) {
            log.info("----- errori nel form");
            bindingResult.getAllErrors().forEach(error -> log.info("---" + error.getDefaultMessage()));
            return "register.html";
        }

        RegistrationDetails details = RegistrationDetails.builder()
                .email(command.getEmail())
                .name(command.getName())
                .privacy(command.privacy)
                .password1(command.getPassword1()) // next hash this password
                .registrationDate(new Date()).build();


        if (this.registrationManager.putIfAbsent(command.getName(), details) == null) {
            log.info("registrazione avvenuta con successo " + details.toString());
            return "redirect:/"; // success

        } else {
            // add user already exist error and return to register
            FieldError error = new FieldError("nameExist", "name", "Username " + command.getName().toString() + " already exist");
            bindingResult.addError(error);
            return "register";
        }


    }

    @GetMapping("/private")
    public String privatePage(HttpSession session) {
        log.info("-------> richiesta pagina private " + session.getAttribute("username"));
        if(session.getAttribute("username")!= null){
            return "private";
        }
        return "redirect:/";

    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        log.info("-------> richiesta logout " + session.getAttribute("username"));
        session.removeAttribute("username");
        return "redirect:/";

    }


}
