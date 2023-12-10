package com.example.apidataib.controller;

import com.example.apidataib.model.СhangeMessageAndButtons;
import com.example.apidataib.model.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MenuController {
    private MainMenu mainMenu;
    private MainMenu menuData;
    private MainMenu menuQuestion;
    private MainMenu menuChange;
    private MainMenu menuHelp;
    private MainMenu menuStuard;
    private MainMenu sourceData;
    private MainMenu sourceChange;
    private MainMenu sourceQuestion;
    @Autowired
    public MenuController(
            MainMenu mainMenu,
            MainMenu menuData,
            MainMenu menuQuestion,
            MainMenu menuChange,
            MainMenu menuHelp,
            MainMenu sourceData,
            MainMenu sourceChange,
            MainMenu menuStuard,
            MainMenu sourceQuestion) {
        this.mainMenu = mainMenu;
        this.menuData = menuData;
        this.menuQuestion = menuQuestion;
        this.menuChange = menuChange;
        this.menuHelp = menuHelp;
        this.sourceData = sourceData;
        this.sourceChange = sourceChange;
        this.menuStuard = menuStuard;
        this.sourceQuestion = sourceQuestion;
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
        correct.put("next_message", "http://localhost:9999/api/find_data/message/repeat");
        correct.put("redirect", null); // пока null, но тут будет еще ссылка на апи
        return correct;
    }

    @GetMapping("/find_data/validation")
    public Map<String, Object> getMenuFindDataValid(@RequestParam("q") String str){
        List<String> buttons = (List<String>) sourceData.getMenu().get("buttonData");
        for (String button : buttons) {
            if (button.equals(str)){
                return getMenuFindDataMessage();
            }
        }
        return СhangeMessageAndButtons.getError(menuData,sourceData);
    }

    @GetMapping("/find_data/message/repeat")
    public Map<String,Object> getRepeat(){
        Map<String,Object> correct = СhangeMessageAndButtons.changeMessageAndButtons(menuData,
                "Хотите повторить процедуру поиска?",true);
        correct.put("next_message", "http://localhost:9999/api/find_data/message/repeat/");
        correct.put("redirect", null);
        return correct;
    }

    @GetMapping("/find_data/message/repeat/{answer}")
    public Map<String,Object> getRepeat(@PathVariable String answer){
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
        correct.put("next_message", "http://localhost:9999/api/stuard");
        correct.put("redirect", null); // добаваить редирект на почту
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
        correct.put("next_message", "http://localhost:9999/api/question/find/");
        correct.put("redirect", null);
        return correct;
    }
    @GetMapping("/question/find/{answer}")
    public Map<String,Object> getFindYesNo(@PathVariable String answer){
        if("Нет".equals(answer)){
            Map<String, Object> correct = СhangeMessageAndButtons.changeMessageAndButtons(menuQuestion,
                    "Опишите вопрос, как можно подробнее. Так же укажите почту для связи.",false);
            correct.put("next_message", "http://localhost:9999/api/stuard");
            correct.put("redirect", null); // добаваить редирект на почту
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
