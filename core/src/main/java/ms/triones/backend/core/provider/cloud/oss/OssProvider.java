package ms.triones.backend.core.provider.cloud.oss;

import com.moensun.csi.api.oss.OssTemplate;
import com.moensun.csi.minio.annotation.MinioClient;

@MinioClient(
        secretKey = "${minio.secret-key}",
        accessKey = "${minio.access-key}",
        endpoint = "${minio.endpoint}",
        bucket = "${minio.bucket}",
        urlPrefix = "${minio.url-prefix}"
)
public interface OssProvider extends OssTemplate {
}
