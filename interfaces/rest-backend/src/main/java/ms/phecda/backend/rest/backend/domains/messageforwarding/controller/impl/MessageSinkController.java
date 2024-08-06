package ms.phecda.backend.rest.backend.domains.messageforwarding.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.service.impl.MessageSinkService;
import ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro.MessageSinkCreateRO;
import ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro.MessageSinkUpdateRO;
import ms.phecda.backend.rest.backend.domains.messageforwarding.internal.MessageForwardingBeRestConvert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ms.phecda.backend.rest.backend.domains.messageforwarding.internal.MessageForwardingConstants.MESSAGE_FORWARDING_URI;

@Tag(name = "消息数据目的")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = MESSAGE_FORWARDING_URI)
public class MessageSinkController {
    private final MessageSinkService messageSinkService;

    @Operation(summary = "创建数据目的")
    @PostMapping(value = "sinks")
    public void createSink(@Validated @RequestBody MessageSinkCreateRO args) {
        MessageSink messageSink = MessageForwardingBeRestConvert.INSTANCE.from(args);
        messageSinkService.create(messageSink);
    }

    @Operation(summary = "根据ID删除数据目的")
    @DeleteMapping(value = "sinks/{id}")
    public void deleteById(@PathVariable(value = "id") String id) {
        messageSinkService.deleteById(id);
    }

    @Operation(summary = "根据ID修改数据目的")
    @PutMapping(value = "sinks/{id}")
    public void updateById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody MessageSinkUpdateRO args) {
        MessageSink messageSink = MessageForwardingBeRestConvert.INSTANCE.from(args);
        messageSink.setId(id);
        messageSinkService.updateById(messageSink);
    }

    @Operation(summary = "根据ID获取数据目的")
    @GetMapping(value = "sinks/{id}")
    public MessageSink querySinkById(@PathVariable(value = "id") String id) {
        return messageSinkService.findById(id).orElse(null);
    }

    @Operation(summary = "获取数据目的列表")
    @GetMapping(value = "sinks/list")
    public List<MessageSink> querySinks() {
        return messageSinkService.findList();
    }
}
