package com.trionesdev.phecda.backend.core.domains.device.internal.util;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductThingModelProfileDTO;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductThingModelProfileDTO.DeviceCommand;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductThingModelProfileDTO.DeviceEvent;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductThingModelProfileDTO.DeviceProperty;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductThingModelProfileDTO.ValueItem;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductThingModelProfileDTO.ValueProperties;
import com.trionesdev.phecda.backend.core.domains.device.internal.aggregate.entity.Product;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand.CallType;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueType;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeArray;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeEnum;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductUtils {

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

    public static ProductThingModelProfileDTO toProductProfile(Product product) {
        var profile = ProductThingModelProfileDTO.builder().productKey(product.getKey()).description(product.getDescription()).build();
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

}
