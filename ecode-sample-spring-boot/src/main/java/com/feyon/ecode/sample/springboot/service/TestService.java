package com.feyon.ecode.sample.springboot.service;

import com.feyon.ecode.core.EcodeUtils;
import com.feyon.ecode.sample.springboot.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Feyon
 */
@Service
public class TestService {

    public String greeting(String  name) {
        if(StringUtils.isEmpty(name)) {
            throw EcodeUtils.toThrow(ErrorCode.NAME_ERROR);
        }
        return "hello " + name;
    }

}
