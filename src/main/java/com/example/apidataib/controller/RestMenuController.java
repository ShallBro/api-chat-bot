package com.example.apidataib.controller;


import com.example.apidataib.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RestMenuController  {
    private final MenuService menuService;
    @Autowired
    public RestMenuController(MenuService menuService) {
        this.menuService = menuService;

    }
    @GetMapping("/input")
    public Map<String,Object> getInput(){
        return menuService.getInput();
    }
    @GetMapping("/menu")
    public Map<String,Object> getMainMenu() {
        return menuService.getMainMenu(false);
    }

    @GetMapping("/find_data")
    public Map<String, Object> getMenuFindData(){
        return menuService.getMenuFindData();
    }

    @GetMapping("/find_data/validation")
    public Map<String, Object> getMenuFindDataValid(@RequestParam("q") String str){
        return menuService.getMenuFindDataValid(str);
    }

    @GetMapping("/find_data/message/repeat")
    public Map<String,Object> getRepeat(){
        return menuService.getRepeat();
    }

    @GetMapping("/find_data/message/repeat/answer")
    public Map<String,Object> getRepeat(@RequestParam("q") String answer){
        return menuService.getRepeat(answer);
    }

    @GetMapping("/change")
    public Map<String, Object> getChangeMenu(boolean error){
        return menuService.getChangeMenu(false);
    }

    @GetMapping("/change/validation")
    public ResponseEntity<?> getMenuChangeValid(@RequestParam("q") String str){
        return menuService.getMenuChangeValid(str);
    }

    @GetMapping("/change/answer")
    public Map<String, Object> getChangeAnswer(String answer){
        return menuService.getChangeAnswer(answer);
    }

    @GetMapping("/question")
    public Map<String,Object> getQuestion(){
        return menuService.getQuestion();
    }

    @GetMapping("/question/find")
    public Map<String,Object> getFindQuestion(){
        return menuService.getFindQuestion();
    }
    @GetMapping("/question/find/answer")
    public Map<String,Object> getFindYesNo(@RequestParam("q") String answer){
        return menuService.getFindYesNo(answer);
    }

    @GetMapping("/help")
    public Map<String,Object> getHelp(){
        return menuService.getHelp();
    }

    @GetMapping("/stuard")
    public Map<String,Object> getStuard(){
        return menuService.getStuard();
    }

    @GetMapping("/validation")
    public ResponseEntity<?> getAnswer(@RequestParam("q") String str){
        return menuService.getAnswer(str);
    }

}
