package com.feyon.ecode.sample;

import com.feyon.ecode.core.EcodeUtils;
import org.springframework.stereotype.Component;

/**
 * @author Feyon
 */
@Component
public class UserService {

    public void login(){
        throw EcodeUtils.newInstance(UserException.class);
    }
}
