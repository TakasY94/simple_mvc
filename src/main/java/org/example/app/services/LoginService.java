package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private Logger logger = Logger.getLogger(LoginService.class);


    public boolean authentificate(LoginForm loginForm){
        logger.info("try auth with user-form: " + loginForm);
        if ( LoginForm.getKeyPas(loginForm.getUsername()).equals(loginForm.getPassword()) ) {
            return true;
        } else return false;
    }

    public boolean registrate(LoginForm loginForm) {
        logger.info("registration new user - " + loginForm.getUsername());
        LoginForm.setKeyPas(loginForm.getUsername(), loginForm.getPassword());
        return true;
    }
}
