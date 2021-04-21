package com.feyon.ecode.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.SimpleEcode;
import org.junit.Test;


public class JsonEcodeFactoryTest {


    @Test
    public void loadAllEcode() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonEcodeFactory factory = new JsonEcodeFactory(objectMapper, SimpleEcode.class);
        factory.reload();
        factory.getAllEcode().forEach(System.out::println);
    }
}