package com.geekcattle.controller.api;

import com.geekcattle.core.LoginEnum;
import com.geekcattle.core.shiro.CustomerAuthenticationToken;
import com.geekcattle.model.member.Member;
import com.geekcattle.model.valid.ValidMember;
import com.geekcattle.service.member.MemberService;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.PasswordUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UuidUtil;
import com.geekcattle.core.jwt.AccessToken;
import com.geekcattle.core.jwt.JwtConfig;
import com.geekcattle.core.jwt.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/oauth")
public class ApiPublicController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ModelMap doLogin(@Valid ValidMember validMember, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ReturnUtil.Error("用户名或密码为空", null, null);
        }
        Example example = new Example(Member.class);
        example.createCriteria()
                .andEqualTo("account", validMember.getAccount());
        Integer userCount = memberService.getCount(example);
        if (userCount == 0) {
            Member member = new Member();
            String Id = UuidUtil.getUUID();
            member.setUid(Id);
            member.setAccount(validMember.getAccount());
            String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
            member.setSalt(salt);
            String password = PasswordUtil.createCustomPwd(validMember.getPassword(), member.getSalt());
            member.setPassword(password);
            member.setState(1);
            member.setCreatedAt(DateUtil.getCurrentTime());
            member.setUpdatedAt(DateUtil.getCurrentTime());
            memberService.insert(member);
        }
        String username = validMember.getAccount();
        CustomerAuthenticationToken token = new CustomerAuthenticationToken(validMember.getAccount(), validMember.getPassword(), false);
        token.setLoginType(LoginEnum.CUSTOMER.toString());
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
        } catch (AuthenticationException ae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("loginType", LoginEnum.CUSTOMER.toString());
            String sessionId = session.getId().toString();
            Member member = (Member) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            String accessToken = jwtUtil.createJWT(member, sessionId, jwtConfig.getSecret());

            //返回accessToken
            AccessToken accessTokenEntity = new AccessToken();
            accessTokenEntity.setAccessToken(accessToken);
            accessTokenEntity.setExpiresIn(jwtConfig.getExpiration());
            accessTokenEntity.setTokenType(jwtConfig.getTokenHead());

            logger.info("前台用户[" + username + "]登录认证通过");
            return ReturnUtil.Success("登录成功", accessTokenEntity);
        } else {
            token.clear();
            return ReturnUtil.Error("登录失败");
        }
    }

}
