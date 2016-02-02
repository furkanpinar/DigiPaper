package com.digipaper.framework.rest;

import com.digipaper.framework.generics.GenericModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ResponseObject<T extends GenericModel> {

    @Getter @Setter String action;
    @Getter @Setter T data;
    @Getter @Setter List<ResponseMessage> message;
}
