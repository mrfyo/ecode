package com.feyon.ecode.core;

import com.feyon.ecode.sample.UserException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Feyon
 */
@SpringBootTest
public class EcodeTest {

    @Test
    void init() {
        System.out.println("hello Ecode");
    }

    @Autowired
    private UserException exception;

    @Test
    void createUserException() {
        String message = exception.getMessage();
        System.out.println(message);
    }

}
