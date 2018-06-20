/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    public JsonUtil() {
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static Object parse(String json) {
        return JSON.parse(json);
    }

    public static String toJsonSuccess(){
        return toJsonSuccess("成功");
    }

    public static String toJsonSuccess(String msg){
        return toJsonSuccess(msg,   new Object());
    }

    public static String toJsonSuccess(Object obj){
        return toJsonSuccess("成功",   obj);
    }

    public static String toJsonSuccess(String msg, Object obj){
        Map<String,Object> mp = new HashMap<String, Object>();
        mp.put("status", 1);
        mp.put("state", "success");
        mp.put("msg", msg.getBytes());
        mp.put("result", obj);
        return toJson(mp);
    }

    public static String toJsonError(){
        return  toJsonError("失败");
    }

    public static String toJsonError(String msg){
        return toJsonError(msg, new Object());
    }

    public static String toJsonError(Object obj){
        return toJsonError("失败", obj);
    }

    public static String toJsonError(String msg, Object obj){
        Map<String,Object> mp = new HashMap<String, Object>();
        mp.put("status", 0);
        mp.put("state", "error");
        mp.put("msg", msg);
        mp.put("result", obj);
        return toJson(mp);
    }
}
