package com.trionesdev.phecda.backend.core.domains.linkage.internal.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperatorEnum {
    EQ("等于", "=="),
    GT("大于", ">"),
    GE("大于等于", ">="),
    LT("小于", "<"),
    LE("小于等于", "<="),
    RANGE_CLOSED("闭区间(a,b)", ""),
    RANGE_OPEN("开区间[a,b]", "");

    private final String label;
    private final String symbol;
}
