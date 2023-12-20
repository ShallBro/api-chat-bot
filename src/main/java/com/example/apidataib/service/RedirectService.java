package com.example.apidataib.service;

import com.example.apidataib.model.URL;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class RedirectService {
    private URL url = new URL();

    private final String strBeg = "https://doc.ru.universe-data.ru/2.5.0-EE/search.html?q=";
    private final String strEnd = "&check_keywords=yes&area=default#";

    public void setUrlMessage(String firstMessage) {
        this.url.setUrlUser(firstMessage);
    }
    public void setUrl(String input){ url.setUrl(input); }

    public ModelAndView findDocumentation(String str){
        String encodedStr = UriUtils.encode(str, "UTF-8");
        String replaceStr = encodedStr.replaceAll(" ","+");
        String url = strBeg + replaceStr + strEnd;
        return new ModelAndView(new RedirectView(url));
    }
    public ModelAndView redirectSite(String str) throws UnsupportedEncodingException {
        String encodedStr = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
        String replaceStr = encodedStr.replace("+", "%20");
        String urlUser = url.getUrl() + "#/data/asset_search/";
        String appendStr = url.parseUrlUser();
        String urlEnd = url.getUrlEnd();
        String url = urlUser + appendStr + replaceStr + urlEnd;
        return new ModelAndView(new RedirectView(url));
    }
}
