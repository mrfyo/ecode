package com.feyon.ecode.sample.springboot.controller;

import com.feyon.ecode.core.EcodeUtils;
import com.feyon.ecode.sample.springboot.TestException;
import com.feyon.ecode.sample.springboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Feyon
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/greet")
    public String greet(String name) {
        return testService.greeting(name);
    }


    @GetMapping("/reload-ecode")
    public String reloadEcode() {
        EcodeUtils.getEcodeManager().reload();
        return "reload success";
    }


    @ExceptionHandler(TestException.class)
    public HttpEntity<String> handleTestException(TestException e) {
        String body = EcodeUtils.getEcode(e).toString();
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
