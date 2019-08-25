package com.hdh.smartlock.shiro;

import com.hdh.smartlock.entity.UserRegisterEntity;
import com.hdh.smartlock.repository.UserRegisterRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @ClassName MyShiroRealm
 * @Description TODO 此类可以获取安全数据源，放入SecurityManager进行身份验证
 * @Author Kikityer
 * @Date 2019/5/7 13:02
 * @Version 1.0.0
 **/
public class MyShiroRealm extends AuthorizingRealm {

    //日志
    private static final Logger log = LoggerFactory.getLogger(MyShiroRealm.class);


    @Autowired
    @Qualifier("userRegisterRepository")
    private UserRegisterRepository userRegisterRepository;
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 将AuthenticationToken强转为UsernamePasswordToken对象
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 获取从表单传过来的用户名
        String username = upToken.getUsername();
        log.info("前端传过来的用户名为："+username);

        UserRegisterEntity adminInfo = userRegisterRepository.findByUsernameOrUserPhoneNum(username,username);
        if (adminInfo == null){
            throw new UnknownAccountException("无此用户名!");
        }
        log.info("user：{}",adminInfo);

        ByteSource credentialsSalt = ByteSource.Util.bytes("156156bjkhi");//MD5加盐
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                username, //用户对象--数据库
                adminInfo.getUserPassword(), //密码--数据库
                credentialsSalt,
                getName()  //realm name
        );
        return simpleAuthenticationInfo;

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


}
