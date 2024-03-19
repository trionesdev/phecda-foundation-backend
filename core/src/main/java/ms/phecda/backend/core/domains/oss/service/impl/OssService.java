package ms.phecda.backend.core.domains.oss.service.impl;


import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.provider.cloud.oss.impl.OssProvider;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class OssService {
    private final OssProvider ossProvider;

    public String putFileObject(String scene, String fileName, InputStream inputStream) {
        return ossProvider.putFileObject(scene, fileName, inputStream);
    }

}
