package ms.phecda.backend.rest.backend.domains.messageforwarding.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSource;
import ms.phecda.backend.core.domains.messageforwarding.service.impl.MessageSourceService;
import ms.phecda.backend.core.dto.messageforwarding.MessageSourceTopicDTO;
import ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro.MessageSourceCreateRO;
import ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro.MessageSourceTopicRO;
import ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro.MessageSourceUpdateRO;
import ms.phecda.backend.rest.backend.domains.messageforwarding.internal.MessageForwardingBeRestConvert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ms.phecda.backend.rest.backend.domains.messageforwarding.internal.MessageForwardingConstants.MESSAGE_FORWARDING_URI;

@Tag(name = "消息数据源")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = MESSAGE_FORWARDING_URI)
public class MessageSourceController {
    private final MessageForwardingBeRestConvert convert;
    private final MessageSourceService messageSourceService;

    @Operation(summary = "创建数据源")
    @PostMapping(value = "sources")
    public void createSource(@Validated @RequestBody MessageSourceCreateRO args) {
        MessageSource messageSource = convert.from(args);
        messageSourceService.create(messageSource);
    }

    @Operation(summary = "根据ID删除数据源")
    @DeleteMapping(value = "sources/{id}")
    public void deleteById(@PathVariable(value = "id") String id) {
        messageSourceService.deleteById(id);
    }

    @Operation(summary = "修改数据源")
    @PutMapping(value = "sources/{id}")
    public void updateById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody MessageSourceUpdateRO args
    ) {
        MessageSource messageSource = MessageForwardingBeRestConvert.INSTANCE.from(args);
        messageSource.setId(id);
        messageSourceService.updateById(messageSource);
    }

    @Operation(summary = "获取消息数据源列表")
    @GetMapping(value = "sources/list")
    public List<MessageSource> queryAll() {
        return messageSourceService.findAll();
    }

    @Operation(summary = "根据ID获取消息数据源")
    @GetMapping(value = "sources/{id}")
    public MessageSource querySourceById(@PathVariable(value = "id") String id) {
        return messageSourceService.findById(id);
    }

    @Operation(summary = "创建消息数据源Topic")
    @PostMapping(value = "sources/{id}/topics")
    public void createSourceTopic(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody MessageSourceTopicRO.Create args) {
        var messageSourceTopic = convert.from(args);
        messageSourceTopic.setSourceId(id);
        messageSourceService.createSourceTopic(messageSourceTopic);
    }

    @Operation(summary = "获取消息数据源的topics")
    @GetMapping(value = "sources/{id}/topics/list")
    public List<MessageSourceTopicDTO> querySourceTopics(
            @PathVariable(value = "id") String id
    ) {
        return messageSourceService.findSourceTopics(id);
    }

    @Operation(summary = "根据Id删除消息数据源Topic")
    @DeleteMapping(value = "sources/{sourceId}/topics/{topicId}")
    public void deleteSourceTopic(
            @PathVariable(value = "sourceId") String sourceId,
            @PathVariable(value = "topicId") String topicId) {
        messageSourceService.deleteSourceTopic(sourceId, topicId);
    }

}
