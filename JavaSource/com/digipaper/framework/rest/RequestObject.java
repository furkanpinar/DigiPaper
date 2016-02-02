package com.digipaper.framework.rest;

import com.digipaper.framework.generics.GenericModel;
import lombok.Getter;
import lombok.Setter;

public class RequestObject<T extends GenericModel> {

    @Getter @Setter String action;
    @Getter @Setter T data;
}
