package com.feyon.ecode.spring;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Feyon
 *
 * JsonEcodeFacory 借助 Jackson 将指定路径下的JSON文件序列化为指定类型（实现 ErrorCode接口），并缓存。
 *
 * 默认使用的Ecode类型 为 {@link SimpleEcode}, json文件中定义的{@link Ecode}必须与{@link Ecode}
 * 的实现类属性保持一致，避免出现序列化错误，具体关于JSON序列化问题，请参考 {@link ObjectMapper}
 *
 */

public class JsonEcodeFactory extends AbstractEcodeFactory {

    private final Logger log = LoggerFactory.getLogger(JsonEcodeFactory.class);

    private final static String DEFAULT_LOCATION = "classpath:ecode";

    private String location = DEFAULT_LOCATION;

    private Class<? extends Ecode> ecodeType;

    private ObjectMapper objectMapper;


    public JsonEcodeFactory(ObjectMapper objectMapper, Class<? extends Ecode> ecodeType) {
        this(DEFAULT_LOCATION, objectMapper, ecodeType);
    }

    public JsonEcodeFactory(String location, ObjectMapper objectMapper, Class<? extends Ecode> ecodeType) {
        this.location = location;
        this.objectMapper = objectMapper;
        this.ecodeType = ecodeType;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEcodeType(Class<? extends Ecode> ecodeType) {
        this.ecodeType = ecodeType;
    }

    public Class<? extends Ecode> getEcodeType() {
        return ecodeType;
    }



    @Override
    public List<Ecode> getAllEcode() {
        List<Ecode> list = new ArrayList<>(16);
        try {
            File dir = ResourceUtils.getFile(location);
            if(dir.isDirectory()) {
                File[] files = dir.listFiles((fp) -> fp.getName().endsWith(".json"));
                if(files != null) {
                    for (File file : files) {
                        list.addAll(loadEcodeFromJson(file));
                    }
                }
            }else {
                throw new FileNotFoundException("the location must be directory, path is " + dir.getPath());
            }
        }catch (IOException fe) {
            log.error("the location must be exist. default location is {}, " +
                    "if you change the directory, please reset the location. {}", DEFAULT_LOCATION, location);
            fe.printStackTrace();
        }
        return list;
    }


    private List<Ecode> loadEcodeFromJson(File file) throws IOException{
        return doLoadEcodeFromJson(file);
    }


    private List<Ecode> doLoadEcodeFromJson(File file) throws IOException{
        List<Ecode> errorCodes = null;
        try {
            Class<? extends Ecode> ecodeType = getEcodeType();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, ecodeType);
            errorCodes = objectMapper.readValue(file, javaType);
        }catch (JsonParseException | JsonMappingException jpe) {
            log.error("json file does not conform to the format, file is " + file.getName());
        }catch (IOException ie) {
            throw new FileNotFoundException("the location must be json file, path is " + file.getPath());
        }
        return errorCodes;
    }


}
