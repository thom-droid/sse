package com.example.sse.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/test1")
    public String test1() {
        log.info("log");
        return "test";
    }
}
