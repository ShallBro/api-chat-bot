package com.example.apidataib.config;

import com.example.apidataib.model.MainMenu;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("com.example.apidataib")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    private final String robot = "Robot";
    @Bean
    public MainMenu mainMenu(){
        Map<String,String> answerBot = new HashMap<>();
        MainMenu mainMenu = new MainMenu("Привет! Я чат бот платформы Universe DG. Чем могу помочь?");
        answerBot.put("name",robot);
        answerBot.put("message",mainMenu.getAnswerBot());
        mainMenu.setMenu("status",200);
        mainMenu.setMenu("buttonData",List.of("Найти необходимые данные","Задать вопрос о платформе",
                                                        "Создать запрос на изменение"));
        mainMenu.setMenu("bubbleData",answerBot);
        return mainMenu;
    }

    @Bean
    @Scope("prototype")
    public MainMenu menuData(){
        Map<String,String> answerBot = new HashMap<>();
        MainMenu menuData = new MainMenu("Выберите тип данных, которые вас интересуют");
        answerBot.put("name",robot);
        answerBot.put("message",menuData.getAnswerBot());
        menuData.setMenu("status",200);
        menuData.setMenu("buttonData",List.of("Проверка качества","Проверка качества FormIT",
                "Правило качества"));
        menuData.setMenu("bubbleData",answerBot);
        return menuData;
    }
    @Bean
    public MainMenu menuQuestion(){
        Map<String,String> answerBot = new HashMap<>();
        MainMenu menuQuestion = new MainMenu("Какой раздел системы Universe DG вас интересует?");
        answerBot.put("name",robot);
        answerBot.put("message",menuQuestion.getAnswerBot());
        menuQuestion.setMenu("status",200);
        menuQuestion.setMenu("buttonData",new ArrayList<>());
        menuQuestion.setMenu("bubbleData",answerBot);
        return menuQuestion;
    }

    @Bean
    @Scope("prototype")
    public MainMenu menuChange(){
        Map<String,String> answerBot = new HashMap<>();
        MainMenu menuChange = new MainMenu("Какого рода ошибка обнаружена?");
        answerBot.put("name",robot);
        answerBot.put("message",menuChange.getAnswerBot());
        menuChange.setMenu("status",200);
        menuChange.setMenu("buttonData",List.of("Неполные данные","Неакутальные данные",
                "Другое"));
        menuChange.setMenu("bubbleData",answerBot);
        return menuChange;
    }

    @Bean
    public MainMenu menuHelp(){
        Map<String,String> answerBot = new HashMap<>();
        MainMenu mainMenu = new MainMenu("Я могу еще чем-то помочь?");
        answerBot.put("name",robot);
        answerBot.put("message",mainMenu.getAnswerBot());
        mainMenu.setMenu("buttonData",List.of("Найти необходимые данные","Задать вопрос о платформе",
                "Создать запрос на изменение"));
        mainMenu.setMenu("status",200);
        mainMenu.setMenu("bubbleData",answerBot);
        return mainMenu;
    }
    @Bean
    public MainMenu menuStuard(){
        Map<String,String> answerBot = new HashMap<>();
        MainMenu menuStuard = new MainMenu("Спасибо за ваш запрос! Он будет передан стюарду данных и проверен");
        answerBot.put("name",robot);
        answerBot.put("message",menuStuard.getAnswerBot());
        menuStuard.setMenu("status",200);
        menuStuard.setMenu("buttonData",new ArrayList<>());
        menuStuard.setMenu("bubbleData",answerBot);
        return menuStuard;
    }
    @Bean
    public MainMenu sourceData(){
        return new MainMenu(menuData());
    }
    @Bean
    public MainMenu sourceChange(){
        return new MainMenu(menuChange());
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(stringConverter);
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
