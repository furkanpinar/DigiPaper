package com.digipaper.framework.rest;

import com.digipaper.framework.generics.GenericModel;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseList<T extends GenericModel> {

    @Getter @Setter String action;
    @Getter @Setter List<T> datas;
    @Getter @Setter List<ResponseMessage> message;

    @PostConstruct
    public void initialize() throws Exception{
        System.out.println("PostConstruct");
        System.out.println("datas size : " + datas.size());

        if(datas != null && datas.size() > 0) {
            for (T data : datas) {
                if(data instanceof GenericModel) {
                    System.out.println(data);
                    try {
                        Class<?> c = Class.forName("com.digipaper.models." + data.getClass().getSimpleName());

                        Method[] methods = c.getDeclaredMethods();
                        for(Method method : methods) {
                            System.out.println(method.getName());
                        }

                        Field[] fields = c.getDeclaredFields();
                        System.out.println("fields size : " + fields.length);
                        for(Field field : fields) {
                            String fieldName = field.getName();
                            Class<?> fieldType = field.getType();

                            System.out.println("Field Name : " + fieldName);
                            if(!"id".equals(fieldName) && !"name".equals(fieldName)) {
                                field.setAccessible(true);
                                System.out.println(field.getClass().getSimpleName());
                                /*
                                if(fieldType.equals(Integer.class)) {
                                    field.set(c, null);
                                } else if (fieldType.equals(String.class)) {
                                    field.set(c, null);
                                } else if (fieldType.equals(GenericModel.class)) {
                                    field.set(c, null);
                                }
                                field.set(c, null);
                                System.out.println("CALL METHOD : " + "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
                                Method method = c.getClass().getDeclaredMethod("set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1), new Class[]{c});
                                method.invoke(c, null);*/
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                        /*Field[] fields = type.getFields();
                        if (fields != null) {
                            for (Field field : fields) {
                                String fieldName = field.getName();
                                System.out.println("Field Name : " + fieldName);
                                if (!"id".equals(fieldName) && !"name".equals(fieldName) && !"code".equals(fieldName)) {
                                    field.setAccessible(false);
                                }
                            }
                        }*/

                } else {
                    System.out.println("Other");
                }
            }
        }
    }
}
