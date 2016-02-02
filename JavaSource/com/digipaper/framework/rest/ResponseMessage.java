package com.digipaper.framework.rest;

import lombok.Getter;
import lombok.Setter;

public class ResponseMessage {

    @Getter @Setter String type;
    @Getter @Setter String message;
}
