package ms.phecda.backend.core.domains.device.dto;

import cn.hutool.core.collection.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand.CallType;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import ms.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueType;
import ms.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeArray;
import ms.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeEnum;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductProfileDTO {
    private String productKey;
    private String manufacturer;
    private String description;
    private List<String> labels;
    private List<DeviceProperty> deviceProperties;
    private List<DeviceCommand> deviceCommands;
    private List<DeviceEvent> deviceEvents;


    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeviceProperty{
        private String identifier;
        private String name;
        private String description;
        private ValueProperties properties;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeviceCommand{
        private String identifier;
        private String name;
        private String description;
        private String callType;
        private List<ValueItem> inputData;
        private List<ValueItem> outputData;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeviceEvent{
        private String identifier;
        private String name;
        private String description;
        private String type;
        private List<ValueItem> outputData;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueItem{
        private String identifier;
        private String name;
        private ValueProperties properties;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueProperties{
        private String valueType;
        private String readWrite;
    }

    public static ProductProfileDTO fromProduct(ProductDTO product){
        var profile = ProductProfileDTO.builder().productKey(product.getKey()).description(product.getDescription()).build();
        var currentThingModel = product.getThingModelCurrent();
        if (Objects.nonNull(currentThingModel) ){
            if (CollectionUtil.isNotEmpty(currentThingModel.getProperties())){
               var deviceProperties = currentThingModel.getProperties().stream().map(thingProperty -> {
                    var property = DeviceProperty.builder().identifier(thingProperty.getIdentifier()).name(thingProperty.getName()).description(thingProperty.getDescription())
                            .properties(
                                    ValueProperties.builder().valueType(valueType(thingProperty.getValueType(), thingProperty.getValueSpec())).readWrite(Optional.ofNullable(thingProperty.getRw()).map(Enum::name).orElse(null)).build()
                            ).build();
                    return property;
                }).collect(Collectors.toList());
                profile.setDeviceProperties(deviceProperties);
            }
            if (CollectionUtil.isNotEmpty(currentThingModel.getCommands())){
                var deviceCommands = currentThingModel.getCommands().stream().map(thingCommand -> {
                    var command = DeviceCommand.builder()
                            .identifier(thingCommand.getIdentifier()).name(thingCommand.getName()).description(thingCommand.getDescription())
                            .callType(Optional.ofNullable(thingCommand.getCallType()).map(CallType::getValue).orElse(null))
                            .inputData(thingCommand.getInputData().stream().map(param -> {
                                return ValueItem.builder().identifier(param.getIdentifier()).name(param.getName())
                                        .properties(
                                                ValueProperties.builder().valueType(valueType(param.getValueType(), param.getValueSpec())).build()
                                        ).build();
                            }).collect(Collectors.toList()))
                            .outputData(thingCommand.getOutputData().stream().map(param -> {
                                return ValueItem.builder().identifier(param.getIdentifier()).name(param.getName())
                                        .properties(
                                                ValueProperties.builder().valueType(valueType(param.getValueType(), param.getValueSpec())).build()
                                        ).build();
                            }).collect(Collectors.toList()))
                            .build();
                    return command;
                }).collect(Collectors.toList());
                profile.setDeviceCommands(deviceCommands);
            }
            if (CollectionUtil.isNotEmpty(currentThingModel.getEvents())){
                var deviceEvents = currentThingModel.getEvents().stream().map(thingEvent -> {
                    var event = DeviceEvent.builder()
                            .identifier(thingEvent.getIdentifier()).name(thingEvent.getName()).description(thingEvent.getDescription())
                            .type(Optional.ofNullable(thingEvent.getType()).map(ThingModelEvent.Type::getValue).orElse(null))
                            .outputData(thingEvent.getOutputData().stream().map(param -> {
                                return ValueItem.builder().identifier(param.getIdentifier()).name(param.getName())
                                        .properties(
                                                ValueProperties.builder().valueType(valueType(param.getValueType(), param.getValueSpec())).build()
                                        ).build();
                            }).collect(Collectors.toList()))
                            .build();
                    return event;
                }).collect(Collectors.toList());
                profile.setDeviceEvents(deviceEvents);
            }
        }
        return profile;
    }

    public static String valueType(ValueTypeEnum valueType, ValueType valueSpec){
        return switch (valueType) {
            case INT, LONG, FLOAT, DOUBLE, BOOL, STRING, STRUCT -> valueType.getValue();
            case ARRAY -> {
                if (Objects.nonNull(valueSpec)) {
                    ValueTypeArray valueArraySpec = (ValueTypeArray) valueSpec;
                    yield Optional.ofNullable(valueArraySpec.getSubValueType()).map(t -> t.getValue() + valueType.getValue()).orElse(valueType.getValue());
                }
                yield valueType.getValue();
            }
        };
    }

}
