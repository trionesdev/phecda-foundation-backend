package com.trionesdev.phecda.infrastructure.tsdb.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


public class TsDbQueryWrapper {
    @Getter
    @Setter
    private String database;
    @Getter
    @Setter
    private String tableName;
    @Getter
    @Setter
    private List<String> selectFields;


    @Getter
    private Map<String,Object> eqCriteria = new HashMap<>();
    @Getter
    private Map<String,Object> ltCriteria = new HashMap<>();
    @Getter
    private Map<String,Object> leCriteria = new HashMap<>();
    @Getter
    private Map<String,Object> gtCriteria = new HashMap<>();
    @Getter
    private Map<String,Object> geCriteria = new HashMap<>();
    public TsDbQueryWrapper eq(String field, Object value){
        eqCriteria.put(field, value);
        return this;
    }

    public TsDbQueryWrapper lt(String field, Object value){
        ltCriteria.put(field, value);
        return this;
    }


    public TsDbQueryWrapper le(String field, Object value){
        leCriteria.put(field, value);
        return this;
    }

    public TsDbQueryWrapper gt(String field, Object value){
        gtCriteria.put(field, value);
        return this;
    }

    public TsDbQueryWrapper ge(String field, Object value){
        geCriteria.put(field, value);
        return this;
    }
}
