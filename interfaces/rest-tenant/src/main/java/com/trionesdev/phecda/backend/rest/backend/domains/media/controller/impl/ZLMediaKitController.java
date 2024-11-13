package com.trionesdev.phecda.backend.rest.backend.domains.media.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.backend.core.domains.device.service.impl.DeviceService;
import com.trionesdev.phecda.backend.rest.backend.domains.media.controller.ro.OnStreamNoneReaderRO;
import com.trionesdev.phecda.backend.rest.backend.domains.media.controller.ro.OnStreamNotFoundRO;
import com.trionesdev.phecda.backend.rest.backend.domains.media.controller.vo.OnStreamNoneReaderVO;
import com.trionesdev.phecda.backend.rest.backend.domains.media.controller.vo.OnStreamNotFoundVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.trionesdev.phecda.backend.rest.backend.domains.media.support.MediaConstants.MEDIA_URI;

@Tag(name = "ZLMediaKit HOOK")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = MEDIA_URI + "/zlmediakit/hook")
public class ZLMediaKitController {
    private final DeviceService deviceService;

    @PostMapping(value = "/on_stream_none_reader")
    public OnStreamNoneReaderVO streamNoneReader(@RequestBody OnStreamNoneReaderRO ro) {
        log.info("[ZLMediaKit] on_stream_none_reader: {}", ro);
        deviceService.stopPushStreamingByName(ro.getStream());
        return OnStreamNoneReaderVO.builder()
                .code(0)
                .close(true)
                .build();
    }

    @PostMapping(value = "/on_stream_not_found")
    public OnStreamNotFoundVO streamNotFound(@RequestBody OnStreamNotFoundRO ro) {
//        log.info("[ZLMediaKit] on_stream_not_found: {}", ro);
//        deviceService.startPushStreamingByName(ro.getStream());
        return OnStreamNotFoundVO.builder()
                .code(0)
                .msg("success")
                .build();
    }
}
