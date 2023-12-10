package com.example.apidataib.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainMenu {
    private Map<String,Object> data = new HashMap<>();
    private String answerBot;

    private MainMenu menuData;

    public MainMenu(MainMenu sourceData) {
        this.data = sourceData.data;
    }

    public MainMenu(String s) {
        answerBot = s;
    }
    public MainMenu getMenuData(){
        return menuData;
    }
    public Map<String,Object> getMenu() {
        return data;
    }

    public String getAnswerBot() {
        return answerBot;
    }
    public void setMenu(String botName, Object message) {
        this.data.put(botName,message);
    }
}
