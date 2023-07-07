package ms.triones.backend.core.provider.cloud.oss;

import com.moensun.csi.api.oss.OssTemplate;
import com.moensun.csi.tencentcloud.cos.annotation.TencentCloudCOSClient;

@TencentCloudCOSClient(
        accessKey = "${tencentcloud.cos.secret-id}",
        secretKey = "${tencentcloud.cos.secret-key}",
        region = "${tencentcloud.cos.region}",
        bucket = "${tencentcloud.cos.bucket}",
        urlPrefix = "${tencentcloud.cos.url-prefix}"
)
public interface OssProvider extends OssTemplate {
}
