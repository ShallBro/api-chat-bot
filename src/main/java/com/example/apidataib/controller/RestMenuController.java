package com.example.apidataib.controller;

import com.example.apidataib.model.СhangeMessageAndButtons;
import com.example.apidataib.model.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RestMenuController  {
    private RedirectController redirectController;
    private MainMenu mainMenu;
    private MainMenu menuData;
    private MainMenu menuQuestion;
    private MainMenu menuChange;
    private MainMenu menuHelp;
    private MainMenu menuStuard;
    private MainMenu sourceData;
    private MainMenu sourceChange;
    private MainMenu sourceQuestion;
    private MainMenu menuInput;
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
        Map<String,Object> correct = СhangeMessageAndButtons.changeMessageAndButtons(menuData,
                "Введите точное наименование или ключевое слово для поиска",false);
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
        Map<String,Object> correct = СhangeMessageAndButtons.changeMessageAndButtons(menuData,
                "Хотите повторить процедуру поиска?",true);
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
            String notFull = "Прикрепите ссылку на данные, которые необходимо дополнить и обоснование для изменения. " +
                            "Так же укажите почту для связи.";
            return ResponseEntity.ok(getChangeAnswer(notFull));
        }
        else if("Неактуальные данные".equals(str)){
            String nonActual = "Прикрепите ссылку на данные, которые необходимо удалить и обоснование для удаления." +
                    "Так же укажите почту для связи.";
            return ResponseEntity.ok(getChangeAnswer(nonActual));
        }
        else if("Другое".equals(str)){
            String another = "Опишите свой запрос, как можно подробнее. Так же укажите почту для связи.";
            return ResponseEntity.ok(getChangeAnswer(another));
        }
        return ResponseEntity.ok(getChangeMenu(true));
    }

    @GetMapping("/change/answer")
    public Map<String, Object> getChangeAnswer(String answer){
        Map<String, Object> correct = СhangeMessageAndButtons.changeMessageAndButtons(menuChange, answer,false);
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
        Map<String, Object> correct = СhangeMessageAndButtons.changeMessageAndButtons(menuQuestion,
                "Вы нашли ответ на наш вопрос?",true);
        correct.put("next_message", "http://localhost:9999/api/question/find/answer?q=");
        correct.put("next_redirect", null);
        return correct;
    }
    @GetMapping("/question/find/answer")
    public Map<String,Object> getFindYesNo(@RequestParam("q") String answer){
        if("Нет".equals(answer)){
            Map<String, Object> correct = СhangeMessageAndButtons.changeMessageAndButtons(menuQuestion,
                    "Опишите вопрос, как можно подробнее. Так же укажите почту для связи.",false);
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
        if("Найти необходимые данные".equals(str)){
            return ResponseEntity.ok(getMenuFindData());
        }
        else if("Задать вопрос о платформе".equals(str)){
            return ResponseEntity.ok(getQuestion());
        }
        else if("Создать запрос на изменение".equals(str)){
            return ResponseEntity.ok(getChangeMenu(false));
        }
        return ResponseEntity.ok(getMainMenu(true));
    }

}
