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
 *  自定义退出
 * @author geekcattle
 */
public class CustomerLogoutFilter extends LogoutFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);

        // 验证是否POST方式
        if (isPostOnlyLogout()) {
            if (!"POST".equals(WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH))) {
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
        } catch (SessionException ise) {
            if(logger.isDebugEnabled()){
                logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
            }
        }
        issueRedirect(request, response, redirectUrl);
        return false;
    }
}
