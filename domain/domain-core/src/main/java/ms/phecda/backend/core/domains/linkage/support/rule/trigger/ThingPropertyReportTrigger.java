package ms.phecda.backend.core.domains.linkage.support.rule.trigger;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.linkage.support.util.RuleUtils;

import java.util.List;
import java.util.Objects;

import static ms.phecda.backend.core.domains.linkage.support.rule.RuleConstants.FACT_DEVICE_NAME;
import static ms.phecda.backend.core.domains.linkage.support.rule.RuleConstants.FACT_PRODUCT_KEY;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
public class ThingPropertyReportTrigger extends EventTrigger {

    @Override
    public String toRuleEl() {
        ThingPropertyReportTrigger.Identifier identifier = (ThingPropertyReportTrigger.Identifier) getIdentifier();
        List<String> rules = Lists.newArrayList();
        if (Objects.nonNull(identifier)) {
            if (StrUtil.isNotBlank(identifier.getProductKey())) {
                rules.add(" " + FACT_PRODUCT_KEY + " == " + RuleUtils.param(identifier.getProductKey(), true));
            }
            if (StrUtil.isNotBlank(identifier.getDeviceName())) {
                rules.add(" " + FACT_DEVICE_NAME + " == " + RuleUtils.param(identifier.getDeviceName(), true));
            }
            EventTrigger.EventFilter filter = getFilter();
            if (Objects.nonNull(filter) && CollectionUtil.isNotEmpty(filter.getArgs())) {
                rules.add(filterEl(identifier.getProperty()));
            }
        }
        return StrUtil.join(" && ", rules);
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Identifier extends EventTrigger.Identifier {
        private String productKey;
        private String deviceName;
        private String property;
    }
}
