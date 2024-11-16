package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSourceRule {
    private String id;
    private String productKey;
    private String deviceName;

    public String conditionEl() {
        List<String> rules = new ArrayList<String>();
        if (StringUtils.isNotBlank(productKey)) {
            if (Objects.equals("+", productKey.trim())) {
                rules.add(" true ");
            } else {
                rules.add(" productKey=\"" + productKey + "\" ");
            }
        }
        if (StringUtils.isNotBlank(deviceName)) {
            if (Objects.equals("+", deviceName.trim())) {
                rules.add(" true ");
            } else {
                rules.add(" deviceName=\"" + deviceName + "\" ");
            }
        }
        return StringUtils.join(rules, " && ");
    }
}
