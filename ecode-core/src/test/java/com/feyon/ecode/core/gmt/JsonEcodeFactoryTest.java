package com.feyon.ecode.core.gmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.EcodeFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class JsonEcodeFactoryTest {

    @Test
    void loadEcode() {
        ObjectMapper mapper = new ObjectMapper();
        JsonEcodeFactory factory = new JsonEcodeFactory(mapper);

    }


    @Test
    void getMessage() {


    }
}