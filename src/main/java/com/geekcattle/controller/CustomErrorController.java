package com.geekcattle.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static CustomErrorController customErrorController;

    @Autowired
    private ErrorAttributes errorAttributes;


    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public CustomErrorController() {
        if(customErrorController == null){
            customErrorController = new CustomErrorController(errorAttributes);
        }
    }

    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(produces = {"text/html"})
    public String errorHtml(Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);
        model.addAttribute("status",status);
        model.addAttribute("error",getErrorAttributes(request, false));
        response.setStatus(status.value());
        return "error/error";
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = this.getErrorAttributes(request, false);
        HttpStatus status = this.getStatus(request);
        return new ResponseEntity(body, status);
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(webRequest,includeStackTrace);
        String URL = request.getRequestURI();
        map.put("url", URL);
        logger.info("CustomErrorController.method [error info]: status-" + map.get("message") +"status-" + map.get("status") +", request url-" + URL);
        return map;
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

}
