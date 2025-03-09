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
    @Setter
    private long limit;


    @Getter
    private Map<String, Object> eqCriteria = new HashMap<>();
    @Getter
    private Map<String, Object> ltCriteria = new HashMap<>();
    @Getter
    private Map<String, Object> leCriteria = new HashMap<>();
    @Getter
    private Map<String, Object> gtCriteria = new HashMap<>();
    @Getter
    private Map<String, Object> geCriteria = new HashMap<>();


    public TsDbQueryWrapper database(String database){
        this.database = database;
        return this;
    }

    public TsDbQueryWrapper table(String table){
        this.tableName = table;
        return this;
    }

    public TsDbQueryWrapper selectFields(List<String> fields){
        this.selectFields = fields;
        return this;
    }

    public TsDbQueryWrapper eq(boolean condition, String field, Object value) {
        if (condition) {
            eqCriteria.put(field, value);
        }
        return this;
    }

    public TsDbQueryWrapper eq(String field, Object value) {
        eqCriteria.put(field, value);
        return this;
    }

    public TsDbQueryWrapper lt(boolean condition, String field, Object value) {
        if (condition) {
            ltCriteria.put(field, value);
        }
        return this;
    }

    public TsDbQueryWrapper lt(String field, Object value) {
        ltCriteria.put(field, value);
        return this;
    }

    public TsDbQueryWrapper le(boolean condition, String field, Object value) {
        if (condition) {
            leCriteria.put(field, value);
        }
        return this;
    }

    public TsDbQueryWrapper le(String field, Object value) {
        leCriteria.put(field, value);
        return this;
    }

    public TsDbQueryWrapper gt(boolean condition, String field, Object value) {
        if (condition) {
            gtCriteria.put(field, value);
        }
        return this;
    }

    public TsDbQueryWrapper gt(String field, Object value) {
        gtCriteria.put(field, value);
        return this;
    }

    public TsDbQueryWrapper ge(boolean condition, String field, Object value) {
        if (condition) {
            geCriteria.put(field, value);
        }
        return this;
    }

    public TsDbQueryWrapper ge(String field, Object value) {
        geCriteria.put(field, value);
        return this;
    }
}
