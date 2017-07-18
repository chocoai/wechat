package com.miyzh.framework.aliyunoss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.log4j.Logger;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.miyzh.framework.util.PropertiesUtil;

/**
 * @Author lagon
 * @time 2016-4-7 下午8:46:54
 * @Description 阿里云OSS简单操作的客户端工具类
 */
public class SimpleAliyunOssClient {
	
//	private static final Logger logger=LoggerFactory.getLogger(SimpleAliyunOssClient.class);
	
	private static Logger logger = Logger.getLogger(SimpleAliyunOssClient.class);
    private static final String endpoint = PropertiesUtil.getPropertyValue("aliyun.oss.endpoint");   
    private static final String accessKeyId = PropertiesUtil.getPropertyValue("aliyun.oss.accessKeyId");
    private static final String accessKeySecret = PropertiesUtil.getPropertyValue("aliyun.oss.accessKeySecret");
    private static final String externalHostName=PropertiesUtil.getPropertyValue("aliyun.oss.myzh.wenwen.external.hostname");
//    private static final String internalHostName=commonResolver.getProperty("aliyun.oss.myzh.wenwen.internal.hostname");
    
    private static OSSClient client = null;
	
    static{
    	//配置连接池
    	ClientConfiguration conf = new ClientConfiguration();
    	conf.setMaxConnections(10);//允许打开的最大HTTP连接数。默认为1024
    	conf.setConnectionTimeout(5000);//建立连接的超时时间（单位：毫秒）。默认为50000毫秒
    	conf.setMaxErrorRetry(3);//可重试的请求失败后最大的重试次数。默认为3次
    	conf.setSocketTimeout(2000);//Socket层传输数据的超时时间（单位：毫秒）。默认为50000毫秒
    	client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
    }
	//创建Bucket
    public static Bucket createBucket(String bucketName){
    	return client.createBucket(bucketName);
    }
    
	//判断Bucket是否存在
    public static boolean doesBucketExist(String bucketName){
    	return client.doesBucketExist(bucketName);
    }
    
    //设置Bucket的ACL权限,Bucket的ACL包含三类：Private（私有）, PublicRead（公共读）, PublicReadWrite（公共读写）
    public static void setBucketAcl(String bucketName, CannedAccessControlList cannedACL){
        client.setBucketAcl(bucketName, CannedAccessControlList.Private);
    }
    
    //获取Bucket的ACL权限
    public static AccessControlList getBucketAcl(String bucketName){
    	AccessControlList acl=client.getBucketAcl(bucketName);
    	return acl;
    }
    
    //删除Bucket,只有当Bucket为空时，才可以通过该方式删除
    public static void deleteBucket(String bucketName){
        client.deleteBucket(bucketName);
    }
    
    //列出所有的Bucket
    public static void listBuckets(){
        System.out.println("Listing buckets");
        for (Bucket bucket : client.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
    }
    
    //上传文件到OSS
    public static boolean uploadFile(String bucketName, String key, File file){
    	PutObjectResult result=client.putObject(new PutObjectRequest(bucketName, key, file));
    	boolean flag=false;
    	if(result!=null){
    		flag=true;
    		logger.info("上传阿里云OSS成功，访问URL:"+externalHostName+"/"+key);
    	}
    	return flag;
    }
    
    //上传文件的byte[]到OSS
    public static boolean uploadFile(String bucketName, String key, byte[] bytes){
    	PutObjectResult result=client.putObject(new PutObjectRequest(bucketName, key, new ByteArrayInputStream(bytes)));
    	boolean flag=false;
    	if(result!=null){
    		flag=true;
    		logger.info("上传阿里云OSS成功，访问URL->"+externalHostName+"/"+key);
    	}else{
    		logger.error("上传阿里云OSS失败!");
    	}
    	return flag;
    }
    
    //获取文件的byte[]
    public static byte[] downloadFile(String bucketName, String key){
    	OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
    	byte[] bytes=null;
		try {
			bytes = IOUtils.toByteArray(object.getObjectContent());
		} catch (IOException e) {
			logger.error("系统异常：{}",e);
		}
    	return bytes;
    }

    //上传对象到OSS,Object须实现Serializable接口
    public static boolean putObject(String bucketName,String key,Serializable object){
    	PutObjectResult result=client.putObject(bucketName, key, new ByteArrayInputStream(SerializationUtils.serialize(object)));
    	boolean flag=false;
    	if(result!=null){
    		flag=true;
    		logger.info("上传阿里云OSS成功，访问URL:"+externalHostName+"/"+key);
    	}
    	return flag;
    }
    
    //上传对象的byte[]到OSS
    public static boolean putObject(String bucketName, String key,byte[] bytes) throws InterruptedException{
    	PutObjectResult result=client.putObject(new PutObjectRequest(bucketName, key, new ByteArrayInputStream(bytes)));
    	boolean flag=false;
    	if(result!=null){
    		flag=true;
    		logger.info("上传阿里云OSS成功，访问URL:"+externalHostName+"/"+key);
    	}
    	return flag;
    }
    
    //获取对象的字符串内容
    public static String getObject(String bucketName, String key) throws IOException{
    	OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
    	String result=IOUtils.toString(object.getObjectContent(),"utf-8");
    	return result;
    }
    
    //删除文件
    public static void deleteFile(String bucketName,String key){
    	client.deleteObject(bucketName, key);
    }
    
    //删除对象
    public static void deleteObject(String bucketName,String key){
    	client.deleteObject(bucketName, key);
    }
    
    public static void main(String[] args) throws Exception {
//    	System.out.println(uploadFile("myzh-wenwen", "test.txt", new File("D:/test.txt")));
//    	Thread.sleep(10);
//    	System.out.println(putObject("myzh-wenwen", "wo", "wo"));
//    	System.out.println(getObject("myzh-wenwen","wo"));
//    	FileUtils.writeByteArrayToFile(new File("D:/11.txt"), downloadFile("myzh-wenwen", "test.txt"));
//    	String key="/chatMessage/10000/2016040900362222303.png";
//    	System.out.println(uploadFile("myzh-wenwen", "heheheh", FileUtils.readFileToByteArray(new File("D:/test.tgz"))));
    	System.out.println(downloadFile("myzh-wenwen", "heheheh"));
	}

}
