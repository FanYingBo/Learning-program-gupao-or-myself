package com.study.gupao.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/hello")
    public String index(){
        return "hello";
    }

    @ModelAttribute
    public Object message(){
        return "";
    }
}
