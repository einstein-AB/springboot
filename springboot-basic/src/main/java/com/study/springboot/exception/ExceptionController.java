package com.study.springboot.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/exception")
public class ExceptionController {

    @GetMapping("/throw")
    public void createException () throws Exception {
        throw new Exception("Message of throwing exception explicitly");
    }
}
