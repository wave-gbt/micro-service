package com.ailk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DemoIndexController {
  
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {  
          
        return "demoIndex.html";
    }  
      
}  