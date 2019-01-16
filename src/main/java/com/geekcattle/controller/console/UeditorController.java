package com.geekcattle.controller.console;

import com.geekcattle.config.UpConfig;
import com.geekcattle.service.common.FileService;
import com.geekcattle.util.JsonUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UuidUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author geekcattle
 */
@Controller
@RequestMapping("/console/ueditor")
public class UeditorController {

    @Value("${ueditor.config.json.path}")
    private String configJSONPath;

    @Resource
    private UpConfig upConfig;

    @Resource
    private FileService fileService;

    @RequestMapping("/save")
    @ResponseBody
    public ModelMap save(String content){
        return ReturnUtil.success(content);
    }


    @RequestMapping
    @ResponseBody
    public void  index(@RequestParam("action") String action, HttpServletRequest request, HttpServletResponse response){
        try {
            PrintWriter writer = response.getWriter();
            if("config".equals(action)){
                //返回配置文件
                InputStream stream = getClass().getClassLoader().getResourceAsStream(configJSONPath);
                File targetFile = new File("config.json");
                FileUtils.copyInputStreamToFile(stream, targetFile);
                writer.write(FileUtils.readFileToString(targetFile,"utf-8"));
            }else if("uploadimage".equals(action) || "uploadscrawl".equals(action) || "uploadvideo".equals(action) || "uploadfile".equals(action)){
                //上传文件
                Map<String,Object> mp = new HashMap<String, Object>(5);
                CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
                //判断 request 是否有文件上传,即多部分请求
                if(multipartResolver.isMultipart(request)){
                    //转换成多部分request
                    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
                    //取得request中的所有文件名
                    Iterator<String> iter = multiRequest.getFileNames();
                    while(iter.hasNext()){
                        //取得上传文件
                        MultipartFile file = multiRequest.getFile(iter.next());
                        if(file != null){
                            if(StringUtils.isEmpty(upConfig.getHardDisk()) && "local".equals(upConfig.getUpType())){
                                mp.put("state","请配置上传目录");
                            }
                            String diskPath = upConfig.getHardDisk();
                            String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                            //验证文件类型
                            if(!fileService.checkExt(extName)){
                                mp.put("state","上传文件格式不支持");
                            }
                            //根据文件类型获取上传目录
                            String uploadPath = fileService.getUploadPath(extName);
                            uploadPath = uploadPath.replace(File.separator,"/");
                            if(StringUtils.isEmpty(uploadPath)){
                                mp.put("state","上传文件路径错误");
                            }
                            String fileName = UuidUtil.getUUID()+extName;
                            String retPath = "";
                            if("local".equals(upConfig.getUpType()) || StringUtils.isEmpty(upConfig.getUpType())){
                                retPath= fileService.fileSave(file, diskPath, uploadPath, fileName);
                            }else if("oss".equals(upConfig.getUpType())){
                                retPath = fileService.ossSave(file, uploadPath, fileName);
                            }else if("qiniu".equals(upConfig.getUpType())){
                                retPath = fileService.qiniuSave(file,uploadPath,fileName);
                            }
                            if("null".equals(retPath)){
                                mp.put("state","上传文件异常");
                            }
                            Map<String, String> upMap = fileService.getReturnMap(retPath, fileName);
                            mp.put("state","SUCCESS");
                            mp.put("url",upMap.get("priviewUrl"));
                            mp.put("title",fileName);
                            mp.put("original",file.getOriginalFilename());
                            writer.print(JsonUtil.toJson(mp));
                        }
                    }
                }
            }
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
