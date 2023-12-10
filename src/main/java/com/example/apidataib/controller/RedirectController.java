package com.example.apidataib.controller;

import com.example.apidataib.model.URL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriUtils;

@Controller
@RequestMapping("/api")
public class RedirectController {
    private URL url;
    private final String strBeg = "https://doc.ru.universe-data.ru/2.5.0-EE/search.html?q=";
    private final String strEnd = "&check_keywords=yes&area=default#";

    @GetMapping("/input")
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

    // http://localhost:8080/api/find_doc?q=Работа с

}
