package it.polito.ai.lab1;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Log(topic = "RegistrationManager")
@Component
public class RegistrationManager extends ConcurrentHashMap <String,RegistrationDetails> {
    public RegistrationManager(){
        log.info("----- Registration manager init");
    }

}
