/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.core;

/**
 * author geekcattle
 * date 2017/3/9 0009 下午 16:02
 */
public enum  LoginEnum {

    CUSTOMER("1"),ADMIN("2");

    private String type;

    private LoginEnum(String type){
        this.type = type;
    }

    @Override
    public  String toString(){
        return this.type.toString();
    }
}
