package com.abdullah.home.automation.utlity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class Util {

    public static final ObjectMapper OBJECT_MAPPER_DEEP_COPY = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    public static Object getDeepCopy(Object o){

        try{
            return OBJECT_MAPPER_DEEP_COPY.readValue(OBJECT_MAPPER_DEEP_COPY.writeValueAsString(o), o.getClass());
        }catch (IOException e){
            System.out.println(e.getMessage());
            //throw new Exception("parse error : " +e.getMessage());

            return null;//will handle later
        }



    }

}
