package com.hdh.smartlock.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @ClassName KaptchaConfig
 * @Description TODO  验证码产生器配置类
 * @Author Kikityer
 * @Date 2019/6/22 17:09
 * @Version 1.0.0
 **/
@Configuration
public class KaptchaConfig {


    @Bean(name="captchaProducer")
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //验证码字符范围
        properties.setProperty("kaptcha.textproducer.char.string", "acdefhk2345678");
        //图片边框颜色
        properties.setProperty("kaptcha.border.color", "245,248,249");
        //字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        //文字间隔
        properties.setProperty("kaptcha.textproducer.char.space", "1");
        //图片宽度
        properties.setProperty("kaptcha.image.width", "100");
        //图片高度
        properties.setProperty("kaptcha.image.height", "35");
        //字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        //session的key
        properties.setProperty("kaptcha.session.key", "code");
        //长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
