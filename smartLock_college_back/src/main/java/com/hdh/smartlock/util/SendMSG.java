package com.hdh.smartlock.util;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.Random;

/**
 * @ClassName SendMSG
 * @Description TODO  腾讯云短信工具类
 * @Author Kikityer
 * @Date 2019/6/22 17:14
 * @Version 1.0.0
 **/
public class SendMSG {


    public String str = "";

    public void send (String phoneNum){
        int appid = 1400212968;  //腾讯短信应用的 SDK AppID
        String appkey = "158c09f39ed081b1e6cabbd9ed22c0aa";//腾讯云短信中的 App Key
        int templateId = 339733;// NOTE: 这里是短信模板ID需要在短信控制台中申请
        String smsSign = "【智能锁管理系统】";//设置信息标头，如【腾讯云】
        //验证码
        try {
            //随机生成6位的验证码
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                str += random.nextInt(10);
            }
            String[] params = {str};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信，这里的13800138000是为用户输入的手机号码
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNum, templateId, params, smsSign, "", "");
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        } catch (com.github.qcloudsms.httpclient.HTTPException e) {
            e.printStackTrace();
        }
    }
}
