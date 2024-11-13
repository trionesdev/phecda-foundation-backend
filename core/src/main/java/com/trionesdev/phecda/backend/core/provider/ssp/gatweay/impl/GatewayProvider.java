package com.trionesdev.phecda.backend.core.provider.ssp.gatweay.impl;

//import com.trionesdev.phecda.gateway.rest.ssp.sdk.CommandReqSO;
//import com.trionesdev.phecda.gateway.rest.ssp.sdk.PhecdaGatewayFeignClient;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.provider.ssp.gatweay.pdo.CommandSendPDO;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class GatewayProvider {
//    private final PhecdaGatewayFeignClient gatewayFeignClient;

    public Map<String, Object> sendCommand(CommandSendPDO command) {
//        CommandReqSO commandReqSO = CommandReqSO.builder()
//                .id(command.getId())
//                .version(command.getVersion())
//                .method(command.getMethod())
//                .productKey(command.getProductKey())
//                .deviceName(command.getDeviceName())
//                .commandName(command.getCommandName())
//                .params(command.getParams())
//                .body(command.getBody())
//                .build();
//        return gatewayFeignClient.sendCommand(commandReqSO);
        return null;
    }

}
