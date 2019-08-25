package com.hdh.smartlock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName CrosConfig
 * @Description TODO  解决跨域问题配置类
 * @Author Kikityer
 * @Date 2019/5/6 10:03
 * @Version 1.0.0
 **/
@Configuration
public class CrosConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名
        corsConfiguration.addAllowedHeader("*"); // 允许任何请求头
        corsConfiguration.addAllowedMethod("*"); // 允许任何方法
        corsConfiguration.setAllowCredentials(true);//允许服务器接收带凭证（cookie、客户端ssl等）的请求******重要****
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
