package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(Model model){
        logger.info("GET /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authentificate(LoginForm loginForm){
        if (loginService.authentificate(loginForm)){
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        }else {
            logger.info("login failed redirect back to login");
            return "redirect:/login";
        }

    }

    @GetMapping("/registration")
    public String registration(Model model){
        logger.info("GET /registration returns registration_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return "registration_page";
    }

    @PostMapping("/registration")
    public String registrateNewUser(LoginForm loginForm){
        if (loginService.registrate(loginForm)){
            logger.info("registration complete");
            return "redirect:/login";
        }else {
            logger.info("registration failed");
            return "redirect:/login/registration";
        }

    }
}
