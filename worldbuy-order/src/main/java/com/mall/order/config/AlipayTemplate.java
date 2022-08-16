package com.mall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "alipay";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC6VoJLZoFx3b/yREB2gAC0CpEtRs3q8+PuYMokO1VkIxMSbRtl2KRwg9Usy65HrUpX/MLe7eeOn/zD6cFraO1xoEQg806Jt9peivZqZnBf+YdcrkZ+yPCuCi6eT84+uRAhTcNDsONXl0wsVAab0pflZIWpibj6cvKCcVcU/go9j4BEu1sShikl3Nttp4dRa3nR7xNdT24upafP1Vrr+MSxk5Ju+g/ws0EPn1dDHKUDPF95xbuw65ASKCTsaKMheeLJ1xu7AwpS1sQx/1O1Wp7KVmwSXLwSsEPrXFAKo/+Ad5OuqFEppIUYzuHcHn9hc6Shd80aYECLtkJuTsfMglHXAgMBAAECggEAf/0hu9apvON6j1aQDJT5ZrEHbNkAYYyqF7dFwUM78O8ij3p/4iG/OUVkLEOu7q65rNwBogw5C78aaBrsbvGAfpZ9lUBpxrCQ/nVmskiwkUuczW0rILS2lGSMBtN8L5r/2FBIeftK/IQAn0YL0AYDrV2xbXUaqHRB3JUrIlEoCYWV/5rKA6NNR+fqeZ0G+rhJqZFVO84YhA1T8YZarHUKFhyfSJeCwUhXyZOLLYLwbORprkiKZcIYMw+xiQIxl09Vrc4B9NYTWpH9tpstMmoHTJU8v2IcRpy7L20C4/SsWLtFShQnWrRQlV/e5sICyAMv60yRyZN0DhzWbTO2D8ioeQKBgQDpLbCRUcd7avJkN/xx/X9dDOAs/xb4pRt7RidUGhkbxpetscj9tgsVFvHT832FTotG9g1YQ3IPf+yQICuF/6JfygtcwRy9zM+LWhfGuJwYmlZCAnoXpEtJAQoP31gSPArrmZBzxrko4gdNGyL1lubTRNHGVCWUyY0FMluwBfeJNQKBgQDMkzl7ALAGEhogTb6Bt7jKcKKDOdVSVvGGEx2cKU0r0fxP5dfuaeMIIHHuhCbTxihPFrGTwnsid7h4S9nIB3dLJX2130dS2Xi0UliZECcUx5n5DEq9LUoqyCcYkKn+YIx+A4Mf7M7fVzuSSDhCyfnSIHup3uu1iW8W51OK84/cWwKBgQCMMc9GIAAq4QspXEvokTxBStuB0kESb9qNDIna0/IitA0//nNUr14MaQYuo+fk/WtZ19gAKFTsosItiVoBr8gFham9M9I+/2QNabZ/4UDgvfJ8r5aOnlYxZ3GQR31hQT5jlqm2IlXzEarIHjevpvHTNj5V2wPZaDDkXfmoZH4GvQKBgBocjJCrpMwRkW4gHjnWZmrId6XCQv0whcF0PSpKh0QZmFfmYWpX75eA9KnaCk6rk0eNAPt4LllW6J/KJrEGau6XiXUmW6cyhNqXTCG1Uxeu5nNIY13EIxxU0dCr3lbOogAPmLQBeOI+8yaXuz6jIxTz+nTTNEjGkvxcaGhq8CjTAoGAdQm7E5Qc2suwqZ+vQ9+bCfDp7OwKoHgHVnL6pYLsNN9psGTX5OQiYLCs1y6+O0mYeOgKwuqhOGgaq+URPXqPc2VdB0l1+Xd+9DxeblDHREpkQJhgYsJ3+hYmnTco72BNyDh/fK2db2mVUhqS9OMg/61s6HTrrCDzU5za1urhXY8=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm2WrMJEGurK23iLityTaPjsbCCUH91/CSgomsrEchfRq+thl9fdFSka4Lz5pehvbCe0Od87ZsGJuyNoIf82vIUTUHkOtpgaeSHSKPiMGcYhjW3wIMBBwvYGUp4ej6kaSFfC0f7aDIy/Q7yZ7EwHzUTRZLt2fE0nUBDzvGsSURbPjNaHR1pgnZoUlqzvmxwASFRF4TPhKeTqNkRY8l48AQwBEkiEYE8HkSY4pX7N//L6owUW7M+AQqlgcV/kmhvZAeJ+9FdJHJt2eyb2lgS+EgSAgSX9lzuykuSliAoJauoXVRAdCddvi0vRMcGNzWJo8pJtJ4eKuozB1oJ+i3stY7wIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url = "https://607a-5-151-139-59.eu.ngrok.io/payed/notify";
//    private  String notify_url = "http://member.worldbuy.com/memberOrder.html";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://member.worldbuy.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 自动关单时间
    private String timeout = "15m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        // 30分钟内不付款就会自动关单
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\"" + timeout + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        return result;
    }
}
