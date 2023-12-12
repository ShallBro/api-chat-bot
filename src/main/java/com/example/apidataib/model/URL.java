package com.example.apidataib.model;

import lombok.Data;

//@Data
public class URL {
    // http://localhost:8080/#/dashboard
    private String url;
    private String urlBegin = "%7B\"searchTerms\"%3A%5B%5D%2C\"sortFields\"%3A%5B%5D%2C\"currentPage\"%3A1%2C\"text\"%3A\"";
    private String urlEnd = "\"%2C\"pageSize\"%3A20%2C\"totalRecords\"%3A0%7D";
    // 3 варианта которые могут быть в строке
    // Проверка качества
    // Проверка качества FormIT
    // Правило качества
    private String urlUser;

    public void setUrlUser(String urlUser) {
        this.urlUser = urlUser;
    }

    public String getUrlEnd() {
        return urlEnd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String parseUrlUser(){
        if (urlUser.equals("Правило качества")){
            return "dq_rule?q=" + urlBegin;
        }
        else if (urlUser.equals("Проверка качества FormIT")){
            return "dq_check_formit?q=" + urlBegin;
        }
        return "dq_check?q=" + urlBegin;
    }
}
