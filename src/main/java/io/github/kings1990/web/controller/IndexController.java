package io.github.kings1990.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class IndexController {

    @GetMapping(value = "")
    public String test() {
        return "rap2/index";
    }
}




