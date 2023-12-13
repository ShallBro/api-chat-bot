package com.example.apidataib.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class СhangeMessageAndButtons {
    private static final List<String> buttons = List.of("Нет","Да");
    public static Map<String, Object> getError(MainMenu mainMenu, MainMenu sourceMenu){
        Map<String,Object> correct = mainMenu.getMenu();
        Map<String,Object> change = (Map<String, Object>) correct.get("bubbleData");
        List<String> buttons = (List<String>) sourceMenu.getMenu().get("buttonData");
        String message = "Ошибка. Введите верный вариант, который я вам предложил";
        change.put("message",message);
        correct.put("next_message",sourceMenu.getMenu().get("next_message"));
        correct.put("status",404);
        correct.put("bubbleData",change);
        correct.put("buttonData",buttons);
        return correct;
    }
    public static Map<String, Object> change(MainMenu mainMenu, String message,boolean addButtons){
        Map<String,Object> correct = mainMenu.getMenu();
        Map<String,Object> change = (Map<String, Object>) correct.get("bubbleData");
        change.put("message",message);
        correct.put("bubbleData",change);
        correct.put("status",200);
        if (addButtons){
            correct.put("buttonData",buttons);
        }
        else {
            correct.put("buttonData",new ArrayList<>());
        }
        return correct;
    }
}
