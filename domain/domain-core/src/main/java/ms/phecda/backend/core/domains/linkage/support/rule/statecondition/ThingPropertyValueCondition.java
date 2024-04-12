package ms.phecda.backend.core.domains.linkage.support.rule.statecondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
public class ThingPropertyValueCondition extends StateCondition {

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StateIdentifier extends StateCondition.StateIdentifier {
        private String productKey;
        private String deviceName;
    }
}
