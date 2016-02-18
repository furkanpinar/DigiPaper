package com.digipaper.framework.query;

import com.digipaper.framework.enums.ClauseType;

import java.util.List;

public class QueryHelper {

    public static void where(List<QueryParam> where, String key, Object value, ClauseType type) {
        if ((value != null && !"".equals(value)) || ClauseType.ISNULL.equals(type) || ClauseType.ISNOTNULL.equals(type) || ClauseType.IN.equals(type))
            where.add(new QueryParam(key, value, type));
    }

    public static void where(List<QueryParam> where, String key, Object value, Object value2, ClauseType type) {
        if ((value != null && !"".equals(value)) || ClauseType.ISNULL.equals(type) || ClauseType.ISNOTNULL.equals(type))
            where.add(new QueryParam(key, value, value2, type));
    }
}
