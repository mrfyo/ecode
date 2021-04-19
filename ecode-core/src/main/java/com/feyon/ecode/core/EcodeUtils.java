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

    public static RuntimeException toThrow(Class<? extends RuntimeException> exType) {
        checkFactory();
        return ecodeManager.getExceptionFactory().newException(exType);
    }

    public static RuntimeException toThrow(String code) {
        checkFactory();
        return ecodeManager.getExceptionFactory().newException(code);
    }

    public static Ecode getEcode(Class<? extends Exception> exType) {
        checkFactory();
        String code = ecodeManager.getEcodeHandler().extractCode(exType);
        return getEcode(code);
    }

    public static Ecode getEcode(String code) {
        return ecodeManager.getEcodeFactory().getEcode(code);
    }


    public static <T extends Ecode>  T getEcode(String code, Class<T> ecodeType) {
        Ecode ecode = getEcode(code);
        if(ecodeType.isInstance(ecode)) {
            return ecodeType.cast(ecode);
        }
        throw new ClassCastException("the ecode is not instance for " + ecodeType);
    }


    private static void checkFactory() {
        if (ecodeManager == null) {
            throw new EcodeException("the exceptionFactory is null. the Ecode can not use.");
        }
    }

}