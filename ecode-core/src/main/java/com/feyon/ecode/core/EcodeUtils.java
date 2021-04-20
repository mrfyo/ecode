package com.feyon.ecode.core;

/**
 * Ecode工具类，主要对 {@link EcodeManager} 的进行简单代理
 *
 * @author Feyon
 */
public class EcodeUtils {

    private static EcodeManager ecodeManager;

    /**
     * 设置 {@link EcodeManager} 并重新加载
     * @param ecodeManager {@link EcodeManager}
     */
    public static void setEcodeManager(EcodeManager ecodeManager) {
        EcodeUtils.ecodeManager = ecodeManager;
        ecodeManager.reload();
    }

    /**
     * 根据指定异常类型 (经过注解{@link EcodeTag}标记过) 动态新建异常对象
     * @param exType 注解{@link EcodeTag}标记过的异常类
     * @return 指定异常类型的异常（已经自动注入指定的错误码消息）
     */
    public static RuntimeException toThrow(Class<? extends RuntimeException> exType) {
        checkManger();
        return ecodeManager.getExceptionFactory().newException(exType);
    }

    /**
     * 根据实现接口{@link EcodeSupport}的异常类及给定的错误码，动态新建异常对象
     * @param code 错误码
     * @param exType 实现接口{@link EcodeSupport}的异常类
     * @return 指定异常类型的异常（已经自动注入指定的错误码消息）
     */
    public static RuntimeException toThrow(String code, Class<? extends RuntimeException> exType) {
        checkManger();
        return ecodeManager.getExceptionFactory().newException(exType, code);
    }

    /**
     * 根据给定的错误码，动态新建异常对象（属于指定默认的并实现{@link EcodeSupport}接口的异常根类型）
     * @see ExceptionFactory
     * @param code 错误码
     * @return 指定异常类型的异常（已经自动注入指定的错误码消息）
     */
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
     * @return 如果 {@link EcodeFactory}存在则返回{@link Ecode}，否则{@link null}
     */
    public static <T extends Ecode> T getEcode(String code, Class<T> ecodeType) {
        Ecode ecode = getEcode(code);
        if (ecodeType.isInstance(ecode)) {
            return ecodeType.cast(ecode);
        }
        throw new ClassCastException("the ecode is not instance for " + ecodeType);
    }

    /**
     * 根据给定的异常对象（实现接口{@link EcodeSupport}）获取,同时返回指定的{@link Ecode}
     * @param exception 异常对象（实现接口{@link EcodeSupport}）
     * @return 如果存在返回 {@link Ecode}，否则为 {@link null}
     */
    public static Ecode getEcode(RuntimeException exception) {
        Ecode ecode = null;
        if(exception instanceof EcodeSupport) {
            ecode = ((EcodeSupport) exception).getEcode();
            if(ecode != null){
                ecode = getEcode(ecode.getCode());
            }
        }
        return ecode;
    }

    public static <T extends Ecode> T getEcode(RuntimeException exception, Class<T> ecodeType) {
        if(exception instanceof EcodeSupport) {
            Ecode ecode =  ((EcodeSupport)exception).getEcode();
            if(ecode != null){
                ecode = getEcode(ecode.getCode());
            }
            if (ecodeType.isInstance(ecode)) {
                return ecodeType.cast(ecode);
            }
            throw new ClassCastException("the ecode is not instance for " + ecodeType);
        }
        return null;
    }


    private static void checkManger() {
        if (ecodeManager == null) {
            throw new EcodeException("the exceptionFactory is null. the Ecode can not use.");
        }
    }

}
