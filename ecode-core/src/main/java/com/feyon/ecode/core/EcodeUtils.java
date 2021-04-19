package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public class EcodeUtils {

    private static EcodeManager ecodeManager;

    public static void setExceptionFactory(EcodeManager ecodeManager) {
        checkFactory();
        EcodeUtils.ecodeManager = ecodeManager;
    }

    public static RuntimeException newInstance(Class<? extends RuntimeException> exType) {
        checkFactory();
        return ecodeManager.newInstance(exType);
    }

    public static RuntimeException newInstance(String code) {
        return ecodeManager.newInstance(code);
    }

    public static Ecode getEcode(Class<? extends Exception> exType) {
        checkFactory();
        String code = ecodeManager.getEcodeHandler().getCode(exType);
        return getEcode(code);
    }

    public static Ecode getEcode(String code) {
        return ecodeManager.getEcode(code);
    }


    private static void checkFactory() {
        if (ecodeManager == null) {
            throw new EcodeException("the exceptionFactory is null. the Ecode can not use.");
        }
    }

}
