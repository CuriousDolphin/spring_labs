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
    public String home(@ModelAttribute("message") String message, Model m) {
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
        // check username sesssion attribute
        if (session.getAttribute("username") != null) {
            return "redirect:/";
        }
        // check form error
        if (bindingResult.hasErrors()) {
            log.info("----- errori nel form di login");
            bindingResult.getAllErrors().forEach(error -> log.info("---" + error.getDefaultMessage()));
            return "login";
        }
        // check user existence
        if (!this.registrationManager.containsKey(command.getEmail())) {
            log.info("----- user " + command.getEmail() + " not exist");
            bindingResult.addError(new ObjectError("error", "user not exist"));
            return "login";
        }
        RegistrationDetails rd = this.registrationManager.get(command.getEmail());
        // check password
        if (command.getPassword().equals(rd.getPassword1())) { // password uguali -> next fare l hash
            log.info("----- user " + command.getEmail() + " LOGGED");
            session.setAttribute("username", command.getEmail());
            return "redirect:/private";
        } else {
            log.info("----- user " + command.getEmail() + " WRONG PASSWORD INSERT");
            bindingResult.addError(new ObjectError("error", "access not allowed"));
            return "login";
        }


    }

    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("command") RegistrationCommand command,HttpSession session) {
        if(session.getAttribute("username")!= null){
            return "redirect:/";
        }
        log.info("-------> richiesta pagina registrazione " + command.toString());

        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("command") RegistrationCommand command,
            BindingResult bindingResult,
            RedirectAttributes ra
    ) {
        log.info("-------> richiesta registrazione " + command.toString());
        if (!command.getPassword1().equals(command.getPassword2())) {
            bindingResult.addError(new FieldError("samePassword", "password2", "The passwords must be the same"));
        }
        if (bindingResult.hasErrors()) {
            log.info("----- errori nel form");
            bindingResult.getAllErrors().forEach(error -> log.info("---" + error.getDefaultMessage()));
            return "register";
        }

        RegistrationDetails details = RegistrationDetails.builder()
                .email(command.getEmail().toLowerCase())
                .name(command.getName())
                .privacy(command.isPrivacy())
                .password1(command.getPassword1()) // next hash this password
                .registrationDate(new Date()).build();


        if (this.registrationManager.putIfAbsent(details.getEmail(), details) == null) {
            log.info("registrazione avvenuta con successo " + details.toString());
            ra.addFlashAttribute("message", "Account created!");
            return "redirect:/"; // success

        } else {
            // add user already exist error and return to register
            FieldError error = new FieldError("mailExist", "email", "Email " + details.getEmail() + " already taken");
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
