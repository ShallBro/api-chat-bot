package com.example.apidataib.controller;

import com.example.apidataib.model.СhangeMessageAndButtons;
import com.example.apidataib.model.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MenuController {
    private MainMenu mainMenu;
    private MainMenu menuData;
    private MainMenu menuQuestion;
    private MainMenu menuChange;
    private MainMenu menuHelp;
    private MainMenu sourceData;
    @Autowired
    public MenuController(
            MainMenu mainMenu,
            MainMenu menuData,
            MainMenu menuQuestion,
            MainMenu menuChange,
            MainMenu menuHelp,
            MainMenu sourceData) {
        this.mainMenu = mainMenu;
        this.menuData = menuData;
        this.menuQuestion = menuQuestion;
        this.menuChange = menuChange;
        this.menuHelp = menuHelp;
        this.sourceData = sourceData;
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
        return menuData.getMenu();
    }

    public Map<String, Object> getMenuFindDataMessage(){
        return СhangeMessageAndButtons.changeMessageAndButtons(menuData,
                "Введите точное наименование или ключевое слово для поиска",false);
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
        return СhangeMessageAndButtons.changeMessageAndButtons(menuData,
                "Хотите повторить процедуру поиска?",true);
    }

    @GetMapping("/find_data/message/repeat/{answer}")
    public Map<String,Object> getRepeat(@PathVariable String answer){
        if("Да".equals(answer)){
            return sourceData.getMenu();
        }
        return getHelp();
    }

    @GetMapping("/change")
    public Map<String, Object> getChangeMenu(){
        return menuChange.getMenu();
    }

    @GetMapping("/question")
    public Map<String,Object> getQuestion(){
        return menuQuestion.getMenu();
    }

    @GetMapping("/question/find")
    public Map<String,Object> getFindQuestion(){
        return СhangeMessageAndButtons.changeMessageAndButtons(menuQuestion,
                "Вы нашли ответ на наш вопрос?",true);
    }
    @GetMapping("/question/find/{answer}")
    public Map<String,Object> getFindYesNo(@PathVariable String answer){
        if("Нет".equals(answer)){
            return СhangeMessageAndButtons.changeMessageAndButtons(menuQuestion,
                    "Опишите вопрос, как можно подробнее. Так же укажите почту для связи.",false);
        }
        return getHelp();
    }

    @GetMapping("/help")
    public Map<String,Object> getHelp(){
        return menuHelp.getMenu();
    }

    @GetMapping("/question/find/no/mail")
    public Map<String,Object> getFindNoMail(){
        return СhangeMessageAndButtons.changeMessageAndButtons(menuQuestion,
                "Спасибо за ваш запрос! Он будет передан стюарду данных и проверен",false);
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
            return ResponseEntity.ok(getChangeMenu());
        }
        return ResponseEntity.ok(getMainMenu(true));
    }

}
