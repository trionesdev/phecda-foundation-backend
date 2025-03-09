package com.trionesdev.phecda.infrastructure.tsdb;

import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbQueryWrapper;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;

public class TsDbUtils {
    public static Expression valueToExpression(Object value) {
        if (value instanceof Integer || value instanceof Long) {
            return new LongValue(Long.parseLong(String.valueOf(value)));
        } else if (value instanceof String) {
            return new StringValue(value.toString());
        } else {
            return new StringValue(value.toString());
        }
    }

    public static PlainSelect toSelectSql(TsDbQueryWrapper wrapper) {
        PlainSelect select = new PlainSelect();
        Table table = new Table().withName(wrapper.getTableName());
        select.withFromItem(table);
        if (CollectionUtils.isNotEmpty(wrapper.getSelectFields())) {
            select.addSelectItems(new Column("time"));
            wrapper.getSelectFields().forEach(field -> {
                select.addSelectItems(new Column(field));
            });
        } else {
            select.addSelectItems(new Column("*"));
        }

        if (MapUtils.isNotEmpty(wrapper.getEqCriteria())) {
            wrapper.getEqCriteria().forEach((key, value) -> {
                Expression whereExpression = new EqualsTo().withLeftExpression(new Column().withColumnName(key))
                        .withRightExpression(valueToExpression(value));
                select.withWhere(whereExpression);
            });
        }
        if (MapUtils.isNotEmpty(wrapper.getGtCriteria())) {
            wrapper.getGtCriteria().forEach((key, value) -> {
                Expression whereExpression = new GreaterThan().withLeftExpression(new Column().withColumnName(key))
                        .withRightExpression(valueToExpression(value));
                select.withWhere(whereExpression);
            });
        }
        if (MapUtils.isNotEmpty(wrapper.getGeCriteria())) {
            wrapper.getGeCriteria().forEach((key, value) -> {
                Expression whereExpression = new GreaterThanEquals().withLeftExpression(new Column().withColumnName(key))
                        .withRightExpression(valueToExpression(value));
                select.withWhere(whereExpression);
            });
        }
        if (MapUtils.isNotEmpty(wrapper.getLtCriteria())) {
            wrapper.getLtCriteria().forEach((key, value) -> {
                Expression whereExpression = new MinorThan().withLeftExpression(new Column().withColumnName(key))
                        .withRightExpression(valueToExpression(value));
                select.withWhere(whereExpression);
            });
        }
        if (MapUtils.isNotEmpty(wrapper.getLeCriteria())) {
            wrapper.getLeCriteria().forEach((key, value) -> {
                Expression whereExpression = new MinorThanEquals().withLeftExpression(new Column().withColumnName(key))
                        .withRightExpression(valueToExpression(value));
                select.withWhere(whereExpression);
            });
        }
        if (wrapper.getLimit() > 0) {
            select.withLimit(new Limit().withRowCount(new LongValue(wrapper.getLimit())));
        }
        select.withOrderByElements(List.of(new OrderByElement().withExpression(new Column("time"))));
        return select;
    }
}
