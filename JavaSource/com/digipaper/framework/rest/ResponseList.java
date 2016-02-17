package com.digipaper.framework.rest;

import com.digipaper.framework.generics.GenericModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
}
