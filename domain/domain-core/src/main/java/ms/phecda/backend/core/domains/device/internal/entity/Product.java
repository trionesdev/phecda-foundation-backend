package ms.phecda.backend.core.domains.device.internal.entity;

import cn.hutool.core.util.StrUtil;
import com.trionesdev.commons.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.dao.po.ProductPO.ProtocolProperty;
import ms.phecda.backend.core.domains.device.internal.enums.AbilityType;
import ms.phecda.backend.core.domains.device.internal.enums.AccessChannel;
import ms.phecda.backend.core.domains.device.internal.enums.NodeType;
import ms.phecda.backend.core.domains.device.internal.enums.ProductStatus;
import ms.phecda.backend.core.domains.device.internal.enums.ProductType;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModel;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class Product {
    private String id;
    private String name;
    private String key;
    private NodeType nodeType;
    private AccessChannel accessChannel;
    private ProductType type;
    private String thingModelVersion;
    private List<ProtocolProperty> protocolProperties;
    private ThingModel thingModelDraft  ;
    private ThingModel thingModelCurrent  ;

    private ProductStatus status;
    private String driverName;

    public void upsertThingModel(ThingModelUpsert upsertCmd) {
        if (Objects.isNull(thingModelDraft)){
            thingModelDraft = new ThingModel();
        }
        if (StrUtil.isBlank(upsertCmd.getIdentifier())){
            if (Objects.nonNull(upsertCmd.getProperty()) &&
                    thingModelDraft.getProperties()
                            .stream()
                            .anyMatch(t -> Objects.equals(upsertCmd.getProperty()
                                    .getIdentifier(), t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }
            if (Objects.nonNull(upsertCmd.getService()) &&
                    thingModelDraft.getCommands()
                            .stream()
                            .anyMatch(t -> Objects.equals(upsertCmd.getService()
                                    .getIdentifier(), t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }
            if (Objects.nonNull(upsertCmd.getEvent()) &&
                    thingModelDraft.getEvents()
                            .stream()
                            .anyMatch(t -> Objects.equals(upsertCmd.getEvent()
                                    .getIdentifier(), t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }
            switch (upsertCmd.getAbilityType()) {
                case PROPERTY:
                    thingModelDraft.getProperties().add(upsertCmd.getProperty());
                    break;
                case COMMAND:
                    thingModelDraft.getCommands().add(upsertCmd.getService());
                    break;
                case EVENT:
                    thingModelDraft.getEvents().add(upsertCmd.getEvent());
                default:
                    break;
            }
        }else {
            switch (upsertCmd.getAbilityType()) {
                case PROPERTY:
                    ThingModelProperty tmp = upsertCmd.getProperty();
                    if (Objects.nonNull(tmp)) {
                        List<ThingModelProperty> properties = thingModelDraft.getProperties().stream().map(t -> {
                            if (Objects.equals(t.getIdentifier(), upsertCmd.getIdentifier())) {
                                return tmp;
                            } else {
                                return t;
                            }
                        }).collect(Collectors.toList());
                        thingModelDraft.setProperties(properties);
                    }
                    break;
                case COMMAND:
                    ThingModelCommand tms = upsertCmd.getService();
                    if (Objects.nonNull(tms)) {
                        List<ThingModelCommand> commands = thingModelDraft.getCommands().stream().map(t -> {
                            if (Objects.equals(t.getIdentifier(), upsertCmd.getIdentifier())) {
                                return tms;
                            } else {
                                return t;
                            }
                        }).collect(Collectors.toList());
                        thingModelDraft.setCommands(commands);
                    }
                    break;
                case EVENT:
                    ThingModelEvent tme = upsertCmd.getEvent();
                    if (Objects.nonNull(tme)) {
                        List<ThingModelEvent> events = thingModelDraft.getEvents().stream().map(t -> {
                            if (Objects.equals(t.getIdentifier(), upsertCmd.getIdentifier())) {
                                return tme;
                            } else {
                                return t;
                            }
                        }).collect(Collectors.toList());
                        thingModelDraft.setEvents(events);
                    }
                    break;
            }
        }
    }


    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThingModelUpsert{
        private AbilityType abilityType;
        private String identifier;
        private ThingModelProperty property;
        private ThingModelEvent event;
        private ThingModelCommand service;
    }

    public void removeThingModelAbility(String identifier){
        if (Objects.nonNull(thingModelDraft)){
            thingModelDraft.getProperties().removeIf((property) -> Objects.equals(identifier, property.getIdentifier()));
            thingModelDraft.getCommands().removeIf((property) -> Objects.equals(identifier, property.getIdentifier()));
            thingModelDraft.getEvents().removeIf((property) -> Objects.equals(identifier, property.getIdentifier()));
        }
    }

}
