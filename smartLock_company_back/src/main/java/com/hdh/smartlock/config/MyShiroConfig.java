package com.hdh.smartlock.config;

import com.hdh.smartlock.shiro.MySessionManager;
import com.hdh.smartlock.shiro.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName MyShiroConfig
 * @Description TODO shiro使用配置类
 * @Author Kikityer
 * @Date 2019/5/7 12:37
 * @Version 1.0.0
 **/
@Configuration
public class MyShiroConfig {
    /**
     * 一、在认证中：
     *    1.1，将加密算法定义好后扔到 MyShiroRealm中 也就是自己定义的realm中
     *    1.2，将MyShiroRealm定义后扔到SecurityManager中。
     *    1.3，后期用到session什么的，都被SecurityManager管理
     *
     * 二、配置session（用Redis存储）
     * 	  2.1 需要配置session，就需要将sessionManager配置在SecurityManager中。
     *    2.2 sessionManager需要交给Redis来管理，所以定义了RedisSessionDAO
     *    2.3 RedisSessionDAO中需要配置Redis的信息，所以定义RedisManager
     */

    @Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;
    @Value("${spring.redis.shiro.timeout}")
    private int timeout;
   /* @Value("${spring.redis.shiro.password}")
    private String password;*/



    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        //AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor  = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor .setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor ;
    }

    //--------------所以接口验证统一管理----------------
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //将表单过滤器放置该容器中
        /*Map<String,Filter> map = new LinkedHashMap<String,Filter>();
        map.put("authc",getFormAuthenticationFilter());*/

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/register/**", "anon"); //注册接口不需要验证是否已经登陆
        filterChainDefinitionMap.put("/user/login", "anon");  //登陆接口不需要验证是否已经登陆
        filterChainDefinitionMap.put("/img/getKaptchaImage","anon");//获取验证码接口
        filterChainDefinitionMap.put("/user/score","anon");//提交评分接口
        filterChainDefinitionMap.put("/forgetPass/**","anon");//忘记密码相关接口
        //配置在最后面
        filterChainDefinitionMap.put("/**", "user");//在认证之后或者开启rememberMe功能之后可以访问的接口
        //登录认证不通过跳转
        shiroFilterFactoryBean.setLoginUrl("/loginUnAuth");
        //权限认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/authorUnAuth");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        shiroFilterFactoryBean.setFilters(map);


        return shiroFilterFactoryBean;
    }




    //-------------------------认证---------------------------
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(myShiroRealm());
        //配置自定义session管理，使用redis
        securityManager.setSessionManager(sessionManager());
        //配置记住我
        securityManager.setRememberMeManager(rememberMeManager());
        //配置redis缓存
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 匹配用户登录使用的令牌和数据库中保存的用户信息是否匹配
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }



    //-------------------------redis-session----------------------
    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
//        SimpleCookie simpleCookie = new SimpleCookie("Token");
//        simpleCookie.setPath("/");
//        simpleCookie.setHttpOnly(false);

        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
//        mySessionManager.setSessionIdCookieEnabled(false);
//        mySessionManager.setDeleteInvalidSessions(true);
//        mySessionManager.setSessionIdCookie(simpleCookie);
        return mySessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setExpire(1800);  //三十分钟会话过期时间
        return redisSessionDAO;
    }

    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
//        redisManager.setPassword(password);
        return redisManager;
    }

    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 这里使用shiro默认拦截器ShiroFilterChainDefinition
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        return chainDefinition;
    }



    //-----------记住密码管理----------
    /**
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * 设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间7天 ,单位秒604800;-->
        simpleCookie.setMaxAge(604800);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * FormAuthenticationFilter 过滤器 过滤记住我
     * @return
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }




    /**
     * shiro自动代理
     * DelegatingFilterProxy作用是自动到spring容器查找名字为shiroFilter（filter-name）的bean并把所有Filter的操作委托给它。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        // shiro starter 默认实现未设置此属性，会导致开启事务的Service无法注入，因此替换默认设置
        daap.setProxyTargetClass(true);
        return daap;
    }



}
