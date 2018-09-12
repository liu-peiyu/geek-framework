/*
 * Copyright (c) 2017-2018.  放牛极客<l_iupeiyu@qq.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * </p>
 *
 */

package com.geekcattle.controller.console;

import com.geekcattle.config.UpConfig;
import com.geekcattle.model.common.Upload;
import com.geekcattle.service.common.FileService;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * author geekcattle
 * date  2017/5/24 0024 下午 3:40.
 */
@Controller
@RequestMapping("/console/upload")
public class FileUploadController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UpConfig upConfig;

    @Resource
    private FileService fileService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(Model model, Upload upload){
        upload.setFileExt(fileService.getFileExt(upload.getFileType()));
        upload.setMaxSize(upConfig.getMaxFileSize());
        model.addAttribute("upload", upload);
        return "console/upload/upload";
    }

    @RequestMapping(value = "/uploader", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap postUploader(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        if (!file.isEmpty()) {
            if(StringUtils.isEmpty(upConfig.getHardDisk()) && "local".equals(upConfig.getUpType())){
                return ReturnUtil.Error("请配置上传目录");
            }
            String diskPath = upConfig.getHardDisk();
            //扩展名格式
            String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //验证文件类型
            if(!fileService.checkExt(extName)){
                return ReturnUtil.Error("上传文件格式不支持");
            }
            //根据文件类型获取上传目录
            String uploadPath = fileService.getUploadPath(extName);
            uploadPath = uploadPath.replace(File.separator,"/");
            if(StringUtils.isEmpty(uploadPath)){
                return ReturnUtil.Error("上传文件路径错误");
            }
            String fileName = UuidUtil.getUUID()+extName;
            String retPath = "";
            if("local".equals(upConfig.getUpType()) && StringUtils.isNotEmpty(upConfig.getUpType())){
                retPath= fileService.fileSave(file, diskPath, uploadPath, fileName);
            }else if("oss".equals(upConfig.getUpType())){
                retPath = fileService.ossSave(file, uploadPath, fileName);
            }else if("qiniu".equals(upConfig.getUpType())){
                retPath = fileService.qiniuSave(file,uploadPath,fileName);
            }
            if("null".equals(retPath)){
                return ReturnUtil.Error("上传文件异常");
            }
            Map<String, String> upMap = fileService.getReturnMap(retPath, fileName);

            return ReturnUtil.Success("上传成功",upMap);
        } else {
            return ReturnUtil.Error("上传文件为空,");
        }
    }



}
