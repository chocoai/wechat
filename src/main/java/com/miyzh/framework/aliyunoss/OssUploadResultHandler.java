package com.miyzh.framework.aliyunoss;

/**
 * @Author liugang
 * @time 2016-4-8 下午1:39:12
 * @Description 阿里云OSS上传结果处理接口
 */
public interface OssUploadResultHandler {

	void handleUploadResult(boolean uploadStatus);

}
