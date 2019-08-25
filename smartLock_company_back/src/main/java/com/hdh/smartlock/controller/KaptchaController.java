package com.hdh.smartlock.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @ClassName KaptchaController
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/10 14:05
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/img")
public class KaptchaController{

    private static final Logger log = LoggerFactory.getLogger(KaptchaController.class);
//    public static final String KEY_CAPTCHA = "KEY_CAPTCHA";

    @Autowired
    private Producer captchaProducer;

    @GetMapping("/getKaptchaImage")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        HttpSession session = request.getSession();
        //将验证码存到session
//        request.getSession().setAttribute("rightCode",capText);
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        System.out.println("获取验证码接口的sessionId为："+session.getId());
        log.info(capText);
        System.out.println(session.getAttribute(Constants.KAPTCHA_SESSION_KEY));
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // 将内存中的图片通过流动形式输出到客户端
        ImageIO.write(bi, "JPEG", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
