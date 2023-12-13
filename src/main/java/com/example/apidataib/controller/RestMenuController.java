package com.example.apidataib.controller;

import com.example.apidataib.model.СhangeMessageAndButtons;
import com.example.apidataib.model.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.apidataib.constants.StringConstants.*;
@RestController
@CrossOrigin
@RequestMapping("/api")
public class RestMenuController  {
    private final RedirectController redirectController;
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
    @Autowired
    public RestMenuController(
            MainMenu mainMenu,
            MainMenu menuData,
            MainMenu menuQuestion,
            MainMenu menuChange,
            MainMenu menuHelp,
            MainMenu sourceData,
            MainMenu sourceChange,
            MainMenu menuStuard,
            MainMenu sourceQuestion,
            MainMenu menuInput,
            RedirectController redirectController) {
        this.mainMenu = mainMenu;
        this.menuData = menuData;
        this.menuQuestion = menuQuestion;
        this.menuChange = menuChange;
        this.menuHelp = menuHelp;
        this.sourceData = sourceData;
        this.sourceChange = sourceChange;
        this.menuStuard = menuStuard;
        this.sourceQuestion = sourceQuestion;
        this.menuInput = menuInput;
        this.redirectController = redirectController;
    }
    @GetMapping("/input")
    public Map<String,Object> getInput(){
        return menuInput.getMenu();
    }
    @GetMapping("/menu")
    public Map<String,Object> getMainMenu(boolean error) {
        if (error){
            return СhangeMessageAndButtons.getError(mainMenu,mainMenu);
        }
        return mainMenu.getMenu();
    }

    @GetMapping("/find_data")
    public Map<String, Object> getMenuFindData(){
        return sourceData.getMenu();
    }

    public Map<String, Object> getMenuFindDataMessage(){
        Map<String,Object> correct = СhangeMessageAndButtons.change(menuData, FIND_MENU_MESSAGE,false);
        correct.put("next_message", "http://localhost:9999/api/find_data/message/repeat?q=");
        correct.put("next_redirect", "http://localhost:9999/api/site?q=");
        return correct;
    }

    @GetMapping("/find_data/validation")
    public Map<String, Object> getMenuFindDataValid(@RequestParam("q") String str){
        List<String> buttons = (List<String>) sourceData.getMenu().get("buttonData");
        for (String button : buttons) {
            if (button.equals(str)){
                redirectController.setUrlMessage(str);
                return getMenuFindDataMessage();
            }
        }
        return СhangeMessageAndButtons.getError(menuData,sourceData);
    }

    @GetMapping("/find_data/message/repeat")
    public Map<String,Object> getRepeat(){
        Map<String,Object> correct = СhangeMessageAndButtons.change(menuData, FIND_MENU_REPEAT,true);
        correct.put("next_message", "http://localhost:9999/api/find_data/message/repeat/answer?q=");
        correct.put("next_redirect", null);
        return correct;
    }

    @GetMapping("/find_data/message/repeat/answer")
    public Map<String,Object> getRepeat(@RequestParam("q") String answer){
        if("Да".equals(answer)){
            return sourceData.getMenu();
        }
        return getHelp();
    }

    @GetMapping("/change")
    public Map<String, Object> getChangeMenu(boolean error){
        if (error){
            return СhangeMessageAndButtons.getError(menuChange,sourceChange);
        }
        return sourceChange.getMenu();
    }

    @GetMapping("/change/validation")
    public ResponseEntity<?> getMenuChangeValid(@RequestParam("q") String str){
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

    @GetMapping("/change/answer")
    public Map<String, Object> getChangeAnswer(String answer){
        Map<String, Object> correct = СhangeMessageAndButtons.change(menuChange, answer,false);
        correct.put("next_message", "http://localhost:9999/api/stuard?q=");
        correct.put("next_redirect", "http://localhost:9999/api/sendMessageOnMail?q=");
        return correct;
    }

    @GetMapping("/question")
    public Map<String,Object> getQuestion(){
        return sourceQuestion.getMenu();
    }

    @GetMapping("/question/find")
    public Map<String,Object> getFindQuestion(){
        Map<String, Object> correct = СhangeMessageAndButtons.change(menuQuestion, QUESTION_FIND,true);
        correct.put("next_message", "http://localhost:9999/api/question/find/answer?q=");
        correct.put("next_redirect", null);
        return correct;
    }
    @GetMapping("/question/find/answer")
    public Map<String,Object> getFindYesNo(@RequestParam("q") String answer){
        if("Нет".equals(answer)){
            Map<String, Object> correct = СhangeMessageAndButtons.change(menuQuestion, QUESTION_MAIL,false);
            correct.put("next_message", "http://localhost:9999/api/stuard?q=");
            correct.put("next_redirect", "http://localhost:9999/api/sendMessageOnMail?q=");
            return correct;
        }
        return getHelp();
    }

    @GetMapping("/help")
    public Map<String,Object> getHelp(){
        return menuHelp.getMenu();
    }

    @GetMapping("/stuard")
    public Map<String,Object> getStuard(){
        return menuStuard.getMenu();
    }

    @GetMapping("/validation")
    public ResponseEntity<?> getAnswer(@RequestParam("q") String str){
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
