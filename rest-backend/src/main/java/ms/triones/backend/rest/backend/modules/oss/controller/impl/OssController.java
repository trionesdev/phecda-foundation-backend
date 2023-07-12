package ms.triones.backend.rest.backend.modules.oss.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.oss.service.impl.OssService;
import ms.triones.backend.rest.backend.modules.oss.controller.vo.UploadVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static ms.triones.backend.rest.backend.modules.oss.support.OssConstants.OSS_URI;

@Tag(name = "文件上传")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = OSS_URI)
public class OssController {
    private final OssService ossService;

    @Operation(summary = "上传文件")
    @PostMapping(value = "file/upload")
    public UploadVO fileUpload(@RequestParam(value = "scene") String scene,
                                @RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String url = ossService.putFileObject(scene, fileName, multipartFile.getInputStream());
        return UploadVO.builder().uid(UUID.randomUUID().toString()).fileName(fileName).url(url).build();
    }
}