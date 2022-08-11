package com.mall.thirdparty.component;

import com.mall.common.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.ali.sms")
@Component
@Data
public class ThirdPartyMsg {

    private String host;
    private String path;
    private String method;
    private String appcode;

    public String SendCode(String phone_number, String code) {
        phone_number = "+"+phone_number;
        String host = "https://intlsms.market.alicloudapi.com";
        String path = "/comms/sms/sendmsgall";
        String method = "POST";
        String appcode = "439775fc63fb4b5d8501c60e69436e55";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("callbackUrl", "http://test.dev.esandcloud.com");
        bodys.put("channel", "0");
        bodys.put("mobile", phone_number);
        bodys.put("templateID", "0000000");
        bodys.put("templateParamSet", code+", 1");
        HttpResponse response = null;


        try {
            response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            if(response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity());
            }
//            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail_" + response.getStatusLine().getStatusCode();
    }
}
