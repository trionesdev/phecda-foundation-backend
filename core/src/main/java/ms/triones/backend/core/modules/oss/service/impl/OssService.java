package ms.triones.backend.core.modules.oss.service.impl;


import com.moensun.commons.core.util.FilePathUtils;
import com.moensun.csi.api.oss.request.OssPutObjectRequest;
import com.moensun.csi.api.oss.response.OssPutObjectResponse;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.provider.cloud.oss.OssProvider;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class OssService {
    private final OssProvider ossProvider;

    public String putImageObject(String scene, String fileName, InputStream inputStream) {
        String objectName = FilePathUtils.pathResolve(imageScene(scene), FilePathUtils.randomFilename(fileName));
        OssPutObjectRequest request = OssPutObjectRequest.builder()
                .objectName(objectName)
                .inputStream(inputStream)
                .build();
        OssPutObjectResponse response = ossProvider.putObject(request);
        return response.getUrl();
    }

    private String imageScene(String scene) {
        StringBuilder sb = new StringBuilder();
        sb.append("image").append("/").append(scene);
        return sb.toString();
    }
}
