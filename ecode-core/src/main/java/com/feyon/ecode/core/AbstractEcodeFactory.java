package com.feyon.ecode.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Feyon
 */
public abstract class AbstractEcodeFactory implements EcodeFactory {

    private final Logger log = LoggerFactory.getLogger(AbstractEcodeFactory.class);

    private final Map<String, Ecode> ecodeCache = new ConcurrentHashMap<>();


    public AbstractEcodeFactory() { }


    @Override
    public String getMessage(String code) {
        Ecode ecode = getEcode(code);
        return ecode != null ? ecode.getMessage() : null;
    }

    @Override
    public Ecode getEcode(String code) {
         return ecodeCache.get(code);
    }

    protected void initEcodeCache() {
        List<Ecode> list = loadAllEcode();
        if(list != null) {
            list.forEach(ecode -> ecodeCache.put(ecode.getCode(), ecode));
        }else {
            throw new RuntimeException("the ecode list cannot be null.");
        }
    }

    /**
     * 加载所有的错误码
     * @return the ecode list, the list is not null.
     */
    public abstract List<Ecode> loadAllEcode();
}
