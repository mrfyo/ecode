package com.feyon.ecode.core.gmt;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.EcodeException;
import com.feyon.ecode.core.EcodeFactory;
import com.feyon.ecode.core.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Feyon
 *
 * JsonEcodeFacory 借助 Jackson 将指定路径下的JSON文件序列化为指定类型（实现 ErrorCode接口），并缓存。
 *
 * 默认使用的ErrorCode类型 为 {@link SimpleErrorCode}
 *
 */

public class JsonEcodeFactory implements EcodeFactory {

    private final Logger log = LoggerFactory.getLogger(JsonEcodeFactory.class);

    private final static String DEFAULT_LOCATION = "classpath:ecode";

    private String location = DEFAULT_LOCATION;

    private final Map<String, ErrorCode> ecodeCache = new ConcurrentHashMap<>();

    private ObjectMapper objectMapper;

    private Class<? extends ErrorCode> supportErrorCode;

    public JsonEcodeFactory(ObjectMapper objectMapper) {
        this(DEFAULT_LOCATION, objectMapper);
    }

    public JsonEcodeFactory(String location, ObjectMapper objectMapper) {
        this.location = location;
        this.objectMapper = objectMapper;
        setSupportErrorCode(SimpleErrorCode.class);
        loadEcode();
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSupportErrorCode(Class<? extends ErrorCode> supportErrorCode) {
        this.supportErrorCode = supportErrorCode;
    }

    @Override
    public String getMessage(String code) {
        Assert.state(objectMapper != null, "the JsonEcodeFactory is need the jackson frame." +
                "please set the objectMapper.");
        ErrorCode errorCode = ecodeCache.get(code);
        if(errorCode != null) {
            return errorCode.getMessage();
        }
        return null;
    }


    private void loadEcode() {
        try {
            File dir = ResourceUtils.getFile(location);
            if(dir.isDirectory()) {
                File[] files = dir.listFiles((fp) -> fp.getName().endsWith(".json"));
                if(files != null) {
                    for (File file : files) {
                        loadEcodeFromJson(file).forEach((ec) -> ecodeCache.put(ec.getCode(), ec));
                    }
                }
            }else {
                throw new EcodeException("the location must be directory, path is " + dir.getPath());
            }
        }catch (FileNotFoundException fe) {
            log.error("the location must be exist. default location is {}, " +
                    "if you change the directory, please reset the location. {}", DEFAULT_LOCATION, location);
            fe.printStackTrace();
        }



    }

    private List<? extends ErrorCode> loadEcodeFromJson(File file){
        return doLoadEcodeFromJson(file, getErrorCodeClass());
    }


    private <T> List<T> doLoadEcodeFromJson(File file, Class<T> clazz) {
        List<T> errorCodes = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            errorCodes = objectMapper.readValue(file, javaType);
        }catch (JsonParseException | JsonMappingException jpe) {
            log.error("json file does not conform to the format, file is " + file.getName());
        }catch (IOException ie) {
            throw new EcodeException("the location must be json file, path is " + file.getPath());
        }
        return errorCodes;
    }



    Class<? extends ErrorCode> getErrorCodeClass() {
        return this.supportErrorCode;
    }

}
