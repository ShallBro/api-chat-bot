package com.example.apidataib.service;

import com.example.apidataib.model.MainMenu;
import com.example.apidataib.model.СhangeMessageAndButtons;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.apidataib.constants.StringConstants.*;
@Service
@AllArgsConstructor
public class MenuService {
    private final RedirectService redirectService;
    private final MainMenu mainMenu;
    private final MainMenu menuData;
    private final MainMenu menuQuestion;
    private final MainMenu menuChange;
    private final MainMenu menuHelp;
    private final MainMenu menuStuard;
    private final MainMenu sourceData;
    private final MainMenu sourceChange;
    private final MainMenu sourceQuestion;
    private final MainMenu menuInput;


    public Map<String,Object> getInput(){
        return menuInput.getMenu();
    }

    public Map<String,Object> getMainMenu(boolean error) {
        if (error){
            return СhangeMessageAndButtons.getError(mainMenu,mainMenu);
        }
        return mainMenu.getMenu();
    }

    public Map<String, Object> getMenuFindData(){
        return sourceData.getMenu();
    }

    public Map<String, Object> getMenuFindDataMessage(){
        Map<String,Object> correct = СhangeMessageAndButtons.change(menuData, FIND_MENU_MESSAGE,false);
        correct.put("next_message", "http://localhost:9999/api/find_data/message/repeat?q=");
        correct.put("next_redirect", "http://localhost:9999/api/site?q=");
        correct.put("next_query",null);
        return correct;
    }

    public Map<String, Object> getMenuFindDataValid(String str){
        List<String> buttons = (List<String>) sourceData.getMenu().get("buttonData");
        for (String button : buttons) {
            if (button.equals(str)){
                redirectService.setUrlMessage(str);
                return getMenuFindDataMessage();
            }
        }
        return СhangeMessageAndButtons.getError(menuData,sourceData);
    }

    public Map<String,Object> getRepeat(){
        Map<String,Object> correct = СhangeMessageAndButtons.change(menuData, FIND_MENU_REPEAT,true);
        correct.put("next_message", "http://localhost:9999/api/find_data/message/repeat/answer?q=");
        correct.put("next_redirect", null);
        correct.put("next_query",null);
        return correct;
    }

    public Map<String,Object> getRepeat(String answer){
        if("Да".equals(answer)){
            return sourceData.getMenu();
        }
        return getHelp();
    }

    public Map<String, Object> getChangeMenu(boolean error){
        if (error){
            return СhangeMessageAndButtons.getError(menuChange,sourceChange);
        }
        return sourceChange.getMenu();
    }

    public ResponseEntity<?> getMenuChangeValid(String str){
        if("Неполные данные".equals(str)){
            return ResponseEntity.ok(getChangeAnswer(NOT_FULL_MESSAGE));
        }
        else if("Неактуальные данные".equals(str)){
            return ResponseEntity.ok(getChangeAnswer(NON_ACTUAL_MESSAGE));
        }
        else if("Другое".equals(str)){
            return ResponseEntity.ok(getChangeAnswer(ANOTHER_MESSAGE));
        }
        return ResponseEntity.ok(getChangeMenu(true));
    }

    public Map<String, Object> getChangeAnswer(String answer){
        Map<String, Object> correct = СhangeMessageAndButtons.change(menuChange, answer,false);
        correct.put("next_message", "http://localhost:9999/api/stuard?q=");
        correct.put("next_redirect", null);
        correct.put("next_query", "http://localhost:9999/api/sendMessageOnMail?q=");
        return correct;
    }
    public Map<String,Object> getQuestion(){
        return sourceQuestion.getMenu();
    }

    public Map<String,Object> getFindQuestion(){
        Map<String, Object> correct = СhangeMessageAndButtons.change(menuQuestion, QUESTION_FIND,true);
        correct.put("next_message", "http://localhost:9999/api/question/find/answer?q=");
        correct.put("next_redirect", null);
        correct.put("next_query",null);
        return correct;
    }

    public Map<String,Object> getFindYesNo(String answer){
        if("Нет".equals(answer)){
            Map<String, Object> correct = СhangeMessageAndButtons.change(menuQuestion, QUESTION_MAIL,false);
            correct.put("next_message", "http://localhost:9999/api/stuard?q=");
            correct.put("next_redirect", null);
            correct.put("next_query", "http://localhost:9999/api/sendMessageOnMail?q=");
            return correct;
        }
        return getHelp();
    }

    public Map<String,Object> getHelp(){
        return menuHelp.getMenu();
    }

    public Map<String,Object> getStuard(){
        return menuStuard.getMenu();
    }

    public ResponseEntity<?> getAnswer(String str){
        if(FIND_MENU.equals(str)){
            return ResponseEntity.ok(getMenuFindData());
        }
        else if(QUESTION_MENU.equals(str)){
            return ResponseEntity.ok(getQuestion());
        }
        else if(CREATE_MENU.equals(str)){
            return ResponseEntity.ok(getChangeMenu(false));
        }
        return ResponseEntity.ok(getMainMenu(true));
    }
}
