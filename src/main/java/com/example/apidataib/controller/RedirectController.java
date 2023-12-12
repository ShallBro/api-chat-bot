package com.example.apidataib.controller;

import com.example.apidataib.model.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
        private URL url = new URL();;

        private final String strBeg = "https://doc.ru.universe-data.ru/2.5.0-EE/search.html?q=";
        private final String strEnd = "&check_keywords=yes&area=default#";

        public void setUrlMessage(String firstMessage) {
            this.url.setUrlUser(firstMessage);
        }
        @GetMapping("/input_url")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void input(@RequestParam("q") String input){
            url.setUrl(input);
        }

        @GetMapping("/find_doc")
        public ModelAndView findDocumentation(@RequestParam("q") String str){
            String encodedStr = UriUtils.encode(str, "UTF-8");
            String replaceStr = encodedStr.replaceAll(" ","+");
            String url = strBeg + replaceStr + strEnd;
            return new ModelAndView(new RedirectView(url));
        }
        @GetMapping("/site")
        public ModelAndView redirectSite(@RequestParam("q") String str) throws UnsupportedEncodingException {
            String encodedStr = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
            String replaceStr = encodedStr.replace("+", "%20");
            String urlUser = url.getUrl() + "#/data/asset_search/";
            String appendStr = url.parseUrlUser();
            String urlEnd = url.getUrlEnd();
            String url = urlUser + appendStr + replaceStr + urlEnd;
            return new ModelAndView(new RedirectView(url));
        }

    }
