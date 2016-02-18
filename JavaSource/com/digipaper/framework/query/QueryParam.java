package com.digipaper.framework.query;

import com.digipaper.framework.enums.ClauseType;
import lombok.Getter;
import lombok.Setter;

public class QueryParam {

    private @Getter @Setter String key;
    private @Getter @Setter Object value;
    private @Getter @Setter Object value2;
    private @Getter @Setter
    ClauseType type;

    public QueryParam(String key, Object value, Object value2, ClauseType type) {
        this.key = key;
        this.value = value;
        this.value2 = value2;
        this.type = type;
    }

    public QueryParam(String key, Object value, ClauseType type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

}
