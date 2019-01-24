package com.geekcattle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 基础配置
 * @author geekcattle
 */
@Configuration
public class UpConfig {

    /**
     * 文件上传方式
     */
    @Value("${upload.config.up-type}")
    private String upType;

    /**
     * 图片访问地址
     */
    @Value("${upload.config.up-cdn}")
    private String upCdn;

    /**
     * 单个文件上传大小，单位为M
     */
    @Value("${upload.config.max-file-size}")
    private Integer maxFileSize;
    /**
     * 路径-保存硬盘地址
     */
    @Value("${upload.config.hard-disk}")
    private String hardDisk;

    /**
     * 默认图片类型文件夹
     */
    @Value("${upload.config.image-folder}")
    private String imageFolder;

    /**
     * 默认文件类型文件夹
     */
    @Value("${upload.config.document-folder}")
    private String documentFolder;

    /**
     * 默认的视频类型文件夹
     */
    @Value("${upload.config.video-folder}")
    private String videoFolder;

    /**
     * 默认的音频类型文件夹
     */
    @Value("${upload.config.music-folder}")
    private String musicFolder;

    /**
     * 图片类型
     */
    @Value("${upload.config.image-type}")
    private String[] imageType;

    /**
     * 文件类型
     */
    @Value("${upload.config.document-type}")
    private String[] documentType;

    /**
     * 视频类型
     */
    @Value("${upload.config.video-type}")
    private String[] videoType;

    /**
     * 音频类型
     */
    @Value("${upload.config.music-type}")
    private String[] musicType;

    public String getUpType() {
        return upType;
    }

    public void setUpType(String upType) {
        this.upType = upType;
    }

    public String getUpCdn() {
        return upCdn;
    }

    public void setUpCdn(String upCdn) {
        this.upCdn = upCdn;
    }

    public Integer getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(Integer maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
        this.imageFolder = imageFolder;
    }

    public String getDocumentFolder() {
        return documentFolder;
    }

    public void setDocumentFolder(String documentFolder) {
        this.documentFolder = documentFolder;
    }

    public String getVideoFolder() {
        return videoFolder;
    }

    public void setVideoFolder(String videoFolder) {
        this.videoFolder = videoFolder;
    }

    public String getMusicFolder() {
        return musicFolder;
    }

    public void setMusicFolder(String musicFolder) {
        this.musicFolder = musicFolder;
    }

    public String[] getImageType() {
        return imageType;
    }

    public void setImageType(String[] imageType) {
        this.imageType = imageType;
    }

    public String[] getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String[] documentType) {
        this.documentType = documentType;
    }

    public String[] getVideoType() {
        return videoType;
    }

    public void setVideoType(String[] videoType) {
        this.videoType = videoType;
    }

    public String[] getMusicType() {
        return musicType;
    }

    public void setMusicType(String[] musicType) {
        this.musicType = musicType;
    }
}
