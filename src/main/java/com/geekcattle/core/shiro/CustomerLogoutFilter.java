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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Locale;

/**
 * 自定义退出
 */
public class CustomerLogoutFilter extends LogoutFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);

        // 验证是否POST方式
        if (isPostOnlyLogout()) {
            if (!WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals("POST")) {
                return onLogoutRequestNotAPost(request, response);
            }
        }
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
            PrincipalCollection principals = subject.getPrincipals();
            RealmSecurityManager securityManager =
                    (RealmSecurityManager) SecurityUtils.getSecurityManager();
            AdminShiroRealm adminRealm = (AdminShiroRealm) securityManager.getRealms().iterator().next();
            adminRealm.clearCachedAuthorizationInfo(principals);
            subject.logout();
            subject.logout();
        } catch (SessionException ise) {
            logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        issueRedirect(request, response, redirectUrl);
        return false;
    }
}
