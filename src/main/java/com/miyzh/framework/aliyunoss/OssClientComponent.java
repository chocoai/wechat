package com.miyzh.framework.aliyunoss;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.miyzh.framework.util.PropertiesUtil;

/**
 * @Author lagon
 * @time 2016-4-8 上午12:54:33
 * @Description OSS客户端封装组件
 */
public class OssClientComponent {

	// private static final Logger
	// logger=LoggerFactory.getLogger(OssClientComponent.class);
	private static Logger logger = Logger.getLogger(OssClientComponent.class);
	private static final String useBucketName = PropertiesUtil
			.getPropertyValue("aliyun.oss.use.bucketName");
	private static final ExecutorService threadPool = Executors
			.newFixedThreadPool(3);// 线程池

	// 上传文件到OSS
	public static void uploadFile(final String savedPath, final File file,
			final OssUploadResultHandler handler) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				boolean flag = SimpleAliyunOssClient.uploadFile(useBucketName,
						savedPath, file);
				// boolean flag=SimpleAliyunOssClient.uploadFile(useBucketName,
				// DigestUtils.md5Hex(savedPath), file);
				// handler.handleUploadResult(flag);
			}
		});
	}

	// 上传文件到OSS
	public static void uploadFile(final String savedPath, final byte[] bytes) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				SimpleAliyunOssClient.uploadFile(useBucketName, savedPath,
						bytes);
				// SimpleAliyunOssClient.uploadFile(useBucketName,
				// DigestUtils.md5Hex(savedPath), bytes);
			}
		});
	}

	// 上传文件的byte[]到OSS
	public static void uploadFile(final String savedPath, final byte[] bytes,
			final OssUploadResultHandler handler) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				boolean flag = SimpleAliyunOssClient.uploadFile(useBucketName,
						savedPath, bytes);
				// boolean flag=SimpleAliyunOssClient.uploadFile(useBucketName,
				// DigestUtils.md5Hex(savedPath), bytes);
				if (flag) {
					logger.error("阿里云OSS上传文件路径【"
							+ savedPath
							+ "】,操作时间【{"
							+ DateFormatUtils.format(new Date(),
									"yyyy-MM-dd HH:mm:ss") + "}】");
				}
				handler.handleUploadResult(flag);
			}
		});
	}

	// 上传文件的byte[]到OSS
	public static boolean uploadFileOrdinary(final String savedPath,
			final byte[] bytes) {

		boolean flag = SimpleAliyunOssClient.uploadFile(useBucketName,
				savedPath, bytes);
		// boolean flag=SimpleAliyunOssClient.uploadFile(useBucketName,
		// DigestUtils.md5Hex(savedPath), bytes);
		if (flag) {
			logger.error("阿里云OSS上传文件路径【" + savedPath + "】,操作时间【{"
					+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
					+ "}】");
		}
		return flag;
	}

	// 获取文件的byte[]
	public static byte[] downloadFile(String savedPath) {
		return SimpleAliyunOssClient.downloadFile(useBucketName, savedPath);
		// return SimpleAliyunOssClient.downloadFile(useBucketName,
		// DigestUtils.md5Hex(savedPath));
	}

	// 删除文件
	public static void deleteFile(String savedPath) {
		SimpleAliyunOssClient.deleteFile(useBucketName, savedPath);
		// SimpleAliyunOssClient.deleteFile(useBucketName,
		// DigestUtils.md5Hex(savedPath));
		logger.info("阿里云OSS删除文件【" + savedPath + "】,操作时间【"
				+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "】");
	}

	// 删除文件异步
	public static void deleteFilesAsync(final List<String> savedPaths) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				if (savedPaths != null && savedPaths.size() > 0) {
					for (String savedPath : savedPaths) {
						try {
							SimpleAliyunOssClient.deleteFile(useBucketName,
									savedPath);
							// SimpleAliyunOssClient.deleteFile(useBucketName,
							// DigestUtils.md5Hex(savedPath));
							logger.info("阿里云OSS删除文件【"
									+ savedPath
									+ "】,操作时间【"
									+ DateFormatUtils.format(new Date(),
											"yyyy-MM-dd HH:mm:ss") + "】");
						} catch (Exception e) {
							logger.info("阿里云OSS删除文件【"
									+ savedPath
									+ "】失败,操作时间【"
									+ DateFormatUtils.format(new Date(),
											"yyyy-MM-dd HH:mm:ss") + "】");
						}
					}
				}
			}

		});
	}

	public boolean flag = true;

	public static void main(String[] args) throws Exception {

		uploadFile(
				"yideb/YYYY/MM/DD/UUID.EXT",
				FileUtils
						.readFileToByteArray(new File(
								"")),
				new OssUploadResultHandler() {

					@Override
					public void handleUploadResult(boolean uploadStatus) {

					}
				});

	}

}
