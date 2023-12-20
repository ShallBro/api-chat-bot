package com.example.apidataib.controller;

import com.example.apidataib.service.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

    @Controller
    @Component
    @CrossOrigin
    @RequestMapping("/api")
    public class RedirectController {
        private RedirectService redirectService;

        @Autowired
        public RedirectController(RedirectService redirectService){
            this.redirectService = redirectService;
        }

        @PostMapping ("/input_url")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void input(@RequestParam("q") String input){
            redirectService.setUrl(input);
        }

        @GetMapping("/find_doc")
        public ModelAndView findDocumentation(@RequestParam("q") String str){
            return redirectService.findDocumentation(str);
        }
        @GetMapping("/site")
        public ModelAndView redirectSite(@RequestParam("q") String str) throws UnsupportedEncodingException {
            return redirectService.redirectSite(str);
        }

    }
