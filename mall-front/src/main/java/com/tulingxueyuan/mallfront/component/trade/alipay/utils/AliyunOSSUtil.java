package com.tulingxueyuan.mallfront.component.trade.alipay.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lightClouds917
 * Date 2018/2/7
 * Description:aliyunOSSUtil
 */
@Slf4j
@Component
public class AliyunOSSUtil {

//这块是从配置文件中拿到值
    @Value("${Oos.endpoint}")
    private String endpoint;
    @Value("${Oos.accessKeyId}")
    private String accessKeyId;
    @Value("${Oos.accessKeySecret}")
    private String accessKeySecret;
    @Value("${Oos.bucketName}")
    private String bucketName;
    @Value("${Oos.dir}")
    private String dir;

    public String upload(File multipartFile) {
        log.info("=========>OSS文件上传开始：" + multipartFile.getName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String datePath = format.format(new Date());
        String originalFilename = multipartFile.getName();
        String fileName = UUID.randomUUID().toString();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newName = fileName + suffix;
        String fileUrl = datePath + "/" + newName;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = new FileInputStream(multipartFile);
            PutObjectResult result = ossClient.putObject(bucketName, multipartFile.getName(), inputStream);
            if (null != result) {
                log.info("==========>OSS文件上传成功,OSS地址：https://" + bucketName + "." + endpoint + "/" + fileUrl);
                return "https://" + bucketName + "." + endpoint + "/" + fileUrl;
            }
        } catch (Exception e) {
        } finally {
            //关闭
            ossClient.shutdown();
        }
        return null;
    }
}
