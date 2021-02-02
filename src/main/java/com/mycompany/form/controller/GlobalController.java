package com.mycompany.form.controller;

import com.mycompany.form.handlers.UserHandler;
import com.mycompany.form.model.UserForm;
import com.mycompany.form.services.EmailService;
import com.mycompany.form.view.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

/**
 * This controller receives requests from users and processes them
 */
@Controller
public class GlobalController {

    private Logger logger = LoggerFactory.getLogger(GlobalController.class);

    UserHandler userHandler;
    EmailService mailSender;

    @Autowired
    public GlobalController(UserHandler userHandler, EmailService mailSender) {
        this.userHandler = userHandler;
        this.mailSender = mailSender;
    }

    @PostMapping("user/create")
    public String createUser(@Valid @ModelAttribute UserForm userForm,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "form";
        }
        try {
            userHandler.writeDataToFile(userForm);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return "redirect:/internalErr";
        }

        logger.info("userForm has been saved = " + userForm);
        try {
            model.addAttribute("userView", userHandler.getUserView(userForm));
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return "redirect:/internalErr";
        }
        return "formResult";
    }

    @GetMapping("user/create")
    public String userCreationForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "form";
    }

    @GetMapping("user/find")
    public String userFindForm(Model model) {
        model.addAttribute("userView", new UserView());
        return "findUser";
    }

    @PostMapping("user/find")
    public String userFind(@RequestHeader(value = "User-Agent") String userAgent,
                           @ModelAttribute UserView userViewIn, Model model) {
        Date date = new Date();

        UserView userView;
        try {
            userView = userHandler.getUserFromFile(userViewIn.getFirstName(), userViewIn.getLastName());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return "redirect:/internalErr";
        }

        if (userView == null) {
            return "redirect:/user/find/not_found";
        }

        model.addAttribute("userView", userView);
        model.addAttribute("userAgent", userAgent);
        model.addAttribute("date", date);
        logger.info("userForm has been found = " + userView);
        return "formResult";
    }

    @GetMapping("user/find/not_found")
    public String userNotFound(Model model) {
        model.addAttribute("message", "user not found");
        return "blankPage";
    }

    @GetMapping("internalErr")
    public String internalError() {
        return "internalServerError";
    }

    @PostMapping("user/sendemail")
    public String sendEmail(Model model, @RequestParam String subject,
                            @RequestParam String text, @RequestParam String to) {
        model.addAttribute("message", "email has sent");

        mailSender.sendSimpleMessage(to, text, subject);

        return "blankPage";
    }
}
