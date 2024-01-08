package com.example.apidataib.controller;

import com.example.apidataib.service.MyMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MailController {

    private MyMailSender mymailSender;

    @Autowired
    public MailController(MyMailSender mailSender) {
        this.mymailSender = mailSender;
    }

    @PostMapping("/sendMessageOnMail")
    public void mailSend(@RequestParam("q") String message) {
        mymailSender.send("Тикет", message);
    }
}
