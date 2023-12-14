package com.example.apidataib.controller;

import com.example.apidataib.service.MyMailSender;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class MailController {

    private MyMailSender mymailSender;

    public MailController(MyMailSender mailSender) {
        this.mymailSender = mailSender;
    }

    @GetMapping("/sendMessageOnMail")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void mailSend(@RequestParam("q") String message){
        mymailSender.send(  "Тикет",message);
    }
}
