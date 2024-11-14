package com.trionesdev.phecda.foundation.core.facade.cloud.oss.impl;

import com.trionesdev.commons.core.util.FilePathUtils;
import com.trionesdev.csi.api.oss.OssTemplate;
import com.trionesdev.csi.api.oss.request.OssPutObjectRequest;
import com.trionesdev.csi.api.oss.response.OssPutObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class OssFacade {
    private final OssTemplate ossTemplate;

    public String putFileObject(String scene, String fileName, InputStream inputStream) {
        String objectName = FilePathUtils.pathResolve(scene, FilePathUtils.randomFilename(fileName));
        OssPutObjectRequest request = OssPutObjectRequest.builder()
                .objectName(objectName)
                .inputStream(inputStream)
                .build();
        OssPutObjectResponse response = ossTemplate.putObject(request);
        return response.getUrl();
    }

}
