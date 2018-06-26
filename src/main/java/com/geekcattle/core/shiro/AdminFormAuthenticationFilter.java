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

package com.geekcattle.core.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * author geekcattle
 * date 2017/3/21 0021 下午 16:33
 */
public class AdminFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    public void setLoginUrl(String loginUrl) {
        super.setLoginUrl("/console/login");
    }

    @Override
    public void setSuccessUrl(String successUrl) {
        super.setSuccessUrl("/console/index");
    }

}
