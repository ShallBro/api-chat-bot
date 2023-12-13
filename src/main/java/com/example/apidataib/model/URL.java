package com.example.apidataib.model;


import static com.example.apidataib.constants.StringConstants.*;

public class URL {
    private String url;
    private String urlUser;

    public void setUrlUser(String urlUser) {
        this.urlUser = urlUser;
    }

    public String getUrlEnd() {
        return CONST_URL_END;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String parseUrlUser(){
        String urlBegin = CONST_URL_BEGIN;
        if (urlUser.equals(RULE)){
            return "dq_rule?q=" + urlBegin;
        }
        else if (urlUser.equals(CHECK_FORMIT)){
            return "dq_check_formit?q=" + urlBegin;
        }
        return "dq_check?q=" + urlBegin;
    }
}
