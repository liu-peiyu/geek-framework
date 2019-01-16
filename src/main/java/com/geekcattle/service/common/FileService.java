package com.geekcattle.service.common;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.geekcattle.config.AliyunConfig;
import com.geekcattle.config.QiniuConfig;
import com.geekcattle.config.UpConfig;
import com.geekcattle.util.DateUtil;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geekcattle
 */
@Service
public class FileService {

    @Autowired
    private UpConfig upConfig;

    @Autowired
    private AliyunConfig aliyunConfig;

    @Autowired
    private QiniuConfig qiniuConfig;


    public Boolean checkExt(String extName) {
        if (this.inArray(upConfig.getImageType(), extName)) {
            return true;
        }
        //视频类型文件
        else if (this.inArray(upConfig.getVideoType(), extName)) {
            return true;
        }
        //文档类型文件
        else if (this.inArray(upConfig.getDocumentType(), extName)) {
            return true;
        }
        //音频类型文件
        else if (this.inArray(upConfig.getMusicType(), extName)) {
            return true;
        } else {
            return false;
        }
    }

    public String getFileExt(String fileType){
        String[] fileExts;
        if(fileType.equals(upConfig.getImageFolder())){
            fileExts = upConfig.getImageType();
        }else if(fileType.equals(upConfig.getDocumentFolder())){
            fileExts = upConfig.getDocumentType();
        }else if(fileType.equals(upConfig.getVideoFolder())){
            fileExts = upConfig.getVideoType();
        }else if(fileType.equals(upConfig.getMusicFolder())){
            fileExts = upConfig.getMusicType();
        }else{
            fileExts = upConfig.getImageType();
        }
        String fileExt = Arrays.toString(fileExts);
        fileExt = fileExt.replace("[","").replace(".","").replace("]","").replace(" ","");
        return fileExt;

    }

    public String getUploadPath(String extName) {
        String filePath = DateUtil.getCurrentYear() + File.separator + DateUtil.getCurrentMonth() + File.separator;

        if(StringUtils.isEmpty(extName)) {
            return filePath;
        }
        /**
         * 根据文件类型选择上传目录
         */
        //图片类型文件
        if (this.inArray(upConfig.getImageType(), extName)) {
            filePath = filePath + upConfig.getImageFolder() + File.separator;
        }
        //视频类型文件
        else if (this.inArray(upConfig.getVideoType(), extName)) {
            filePath = filePath + upConfig.getVideoFolder() + File.separator;
        }
        //文档类型文件
        else if (this.inArray(upConfig.getDocumentType(), extName)) {
            filePath = filePath + upConfig.getDocumentFolder() + File.separator;
        }
        //音频类型文件
        else if (this.inArray(upConfig.getMusicType(), extName)) {
            filePath = filePath + upConfig.getMusicFolder() + File.separator;
        } else {
            return filePath;
        }
        return filePath;
    }

    /**
     * @param file     //文件对象
     * @param filePath //上传路径
     * @param fileName //文件名
     * @return 文件名
     */
    public String fileSave(MultipartFile file, String diskPath, String filePath, String fileName) {
        try {
            copyFile(file.getInputStream(), diskPath + filePath, fileName);
            return filePath + fileName;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * 本地文件上传
     * @param in
     * @param dir
     * @param realName
     * @throws IOException
     */
    private static String copyFile(InputStream in, String dir, String realName)
            throws IOException {
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        FileUtils.copyInputStreamToFile(in, file);
        return realName;
    }

    /**
     * 判断数组中是否包含某个元素
     * @param array   类型的数组
     * @param element 被检查的类型
     * @return
     */
    private boolean inArray(String[] array, String element) {
        boolean flag = false;
        for (String type : array) {
            if (element.equals(type)) {
                flag = true;
                break;
            }
        }
        return flag;
    }



    /**
     * 创建目录
     *
     * @param destDirName 目标目录名
     * @return 目录创建成功返回true，否则返回false
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建单个目录
        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param filePathAndName
     * @return boolean
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myDelFile = new File(filePath);
            myDelFile.delete();
        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 读取到字节数组
     *
     * @param filePath //路径
     * @throws IOException
     */
    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }

    /**
     * 七牛上传
     * @param file
     * @param filePath
     * @param fileName
     * @return
     */
    public String qiniuSave(MultipartFile file, String filePath, String fileName) {
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());
        String key = filePath + fileName;
        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 阿里OSS上传
     * @param file
     * @param filePath
     * @param fileName
     * @return
     */
    public String ossSave(MultipartFile file, String filePath, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(aliyunConfig.getEndpoint(),aliyunConfig.getAk(), aliyunConfig.getSk());
        String key = filePath + fileName;
        try {
            PutObjectResult objectResult = ossClient.putObject(aliyunConfig.getBucket(),   key,    file.getInputStream());
            return key;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return null;
    }

    public Map<String, String> getReturnMap(String retPath, String fileName) {
        String cdnUrl = upConfig.getUpCdn();
        if ("oss".equals(upConfig.getUpType())) {
            cdnUrl = aliyunConfig.getDomain();
        }else if("qiniu".equals(upConfig.getUpType())){
            cdnUrl = qiniuConfig.getDomain();
        }
        String previewUrl = cdnUrl + "/" + retPath.replace(File.separator, "/");
        Map<String, String> upMap = new HashMap<>(3);
        upMap.put("priviewUrl", previewUrl);
        upMap.put("filePath", retPath);
        upMap.put("fileName", fileName);
        return upMap;
    }

}
