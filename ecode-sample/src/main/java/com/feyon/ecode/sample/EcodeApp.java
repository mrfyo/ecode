package com.feyon.ecode.sample;

import com.feyon.ecode.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feyon
 */
public class EcodeApp {

    public void configEcode() {
        EcodeFactory ecodeFactory = new AbstractEcodeFactory() {
            @Override
            public List<Ecode> getAllEcode() {
                List<Ecode> ecodeList = new ArrayList<>(15);
                ecodeList.add(new SimpleEcode("10000", "用户错误"));
                ecodeList.add(new SimpleEcode("20000", "系统错误"));
                return ecodeList;
            }
        };
        EcodeHandler ecodeHandler = new AnnotationEcodeHandler();
        ExceptionFactory exceptionFactory = new SimpleExceptionFactory(ecodeFactory, ecodeHandler);
        EcodeManager manager = new DefaultEcodeManager(exceptionFactory, ecodeFactory, ecodeHandler);
        EcodeUtils.setEcodeManager(manager);
    }

    public void service() {
        System.out.println("start service");
        throw EcodeUtils.toThrow("10000", UserException.class);
    }

    public void tryService() {
        try {
            service();
        }catch (RuntimeException e) {
            Ecode ecode = EcodeUtils.getEcode(e);
            System.out.println(ecode);
        }
    }


    public static void main(String[] args) {
        EcodeApp ecodeApp = new EcodeApp();
        ecodeApp.configEcode();

        ecodeApp.tryService();
    }

}
