package com.trionesdev.phecda.backend.core.domains.oss.service.impl;


import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.facade.cloud.oss.impl.OssFacade;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class OssService {
    private final OssFacade ossFacade;

    public String putFileObject(String scene, String fileName, InputStream inputStream) {
        return ossFacade.putFileObject(scene, fileName, inputStream);
    }

}
