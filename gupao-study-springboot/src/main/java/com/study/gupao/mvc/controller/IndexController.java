package com.study.gupao.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController {

    @GetMapping("/")
    public Object index(){
        return "";
    }

    @ModelAttribute
    public Object message(){
        return "";
    }
}
