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

package com.geekcattle.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * ID工具类
 * author geekcattle
 * date 2016/11/23 0023 下午 14:53
 */
public class IdUtil {
    public static String uuid() {
        return uuid16();
    }

    public static String uuid64() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base64.encodeBase64URLSafeString(bb.array());
    }

    public static String uuid16() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").toLowerCase();
    }

    public static String randomNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String randomAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String randomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    private static final SimpleDateFormat timeSdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    synchronized public static String timeId() {
        StringBuilder sb = new StringBuilder();
        sb.append(timeSdf.format(System.currentTimeMillis()));
        sb.append(randomNumeric(5));
        return sb.toString();
    }
}
