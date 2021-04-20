package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public class EcodeUtils {

    private static EcodeManager ecodeManager;

    public static void setEcodeManager(EcodeManager ecodeManager) {
        EcodeUtils.ecodeManager = ecodeManager;
    }

    public static RuntimeException toThrow(Class<? extends RuntimeException> exType) {
        checkManger();
        return ecodeManager.getExceptionFactory().newException(exType);
    }

    public static RuntimeException toThrow(String code, Class<? extends RuntimeException> exType) {
        checkManger();
        return ecodeManager.getExceptionFactory().newException(exType, code);
    }

    public static RuntimeException toThrow(String code) {
        checkManger();
        return ecodeManager.getExceptionFactory().newException(code);
    }

    /**
     * 从标记了注解{@link EcodeTag}的异常类，从{@link EcodeFactory}获取{@link Ecode}
     * @param exType 标记了注解{@link EcodeTag} 的异常类
     * @return {@link Ecode}
     */
    public static Ecode getEcode(Class<? extends Exception> exType) {
        checkManger();
        String code = ecodeManager.getEcodeHandler().extractCode(exType);
        return getEcode(code);
    }

    /**
     * 根据给定的{@link EcodeFactory}读取{@link Ecode}
     * @param code 错误码
     * @return 如果 {@link EcodeFactory}存在则返回{@link Ecode}，否则返回 null
     */
    public static Ecode getEcode(String code) {
        return ecodeManager.getEcodeFactory().getEcode(code);
    }

    /**
     * 根据给定的{@link EcodeFactory}读取{@link Ecode}，同时返回指定的{@link Ecode}
     * @param code 错误码
     * @param <T> {@link Ecode}的实现类，默认是{@link SimpleEcode}
     * @return 如果 {@link EcodeFactory}存在则返回{@link Ecode}，否则返回 null
     */
    public static <T extends Ecode> T getEcode(String code, Class<T> ecodeType) {
        Ecode ecode = getEcode(code);
        if (ecodeType.isInstance(ecode)) {
            return ecodeType.cast(ecode);
        }
        throw new ClassCastException("the ecode is not instance for " + ecodeType);
    }


    private static void checkManger() {
        if (ecodeManager == null) {
            throw new EcodeException("the exceptionFactory is null. the Ecode can not use.");
        }
    }

}
