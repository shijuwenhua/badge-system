package com.shijuwenhua.signin.utils;

import java.net.URI;
import java.security.MessageDigest;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
@Configuration
public class LoginUtils{

    private static final long serialVersionUID = 1L;

    private static String APPID = "";
    private static String SECRET = "";
    private static String accessToken = null;

    @Value("${weapp.id}")
    public void setAppId(String app_id){
        this.APPID = app_id;
    }

    @Value("${weapp.secret}")
    public void setAppSecret(String app_secret){
        this.SECRET = app_secret;
    }

    public static String getSha1(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        }catch (Exception e){
            return null;
        }
    }

    //获取凭证校检接口
    public static String getOpenId(String code)  
    {
        //微信的接口
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+
                "&secret="+SECRET+"&js_code="+ code +"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        //进行网络请求,访问url接口
        ResponseEntity<String>  responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);  
        //根据返回值进行后续操作 
        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String sessionData = responseEntity.getBody();
            JsonParser parser =new JsonParser(); 
            // Gson gson = new Gson();
            // //解析从微信服务器获得的openid和session_key;
            // WeChatSession weChatSession = gson.fromJson(sessionData,WeChatSession.class);
            // //获取用户的唯一标识
            // String openid = weChatSession.getOpenid();
            // //获取会话秘钥
            // String session_key = weChatSession.getSession_key();
            // return weChatSession;
            JsonObject result = parser.parse(sessionData).getAsJsonObject();
            JsonElement openId = result.get("openid");
            if ( null != result.get("openid")){
                return openId.getAsString();
            }else {
                String errCode = result.get("errcode").getAsString();
                String errMsg = result.get("errmsg").getAsString();
                System.out.println("errcode: " + errCode + ", errmsg: " + errMsg);
                return null;
            }
        }else{
            return null;
        }		
    }

    private static void setAccessToken() {
        //微信的接口
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+
                "&secret="+SECRET+"&grant_type=client_credential";
        RestTemplate restTemplate = new RestTemplate();
        //进行网络请求,访问url接口
        ResponseEntity<String>  responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);  
        //根据返回值进行后续操作 
        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String sessionData = responseEntity.getBody();
            JsonParser parser =new JsonParser(); 
            JsonObject result = parser.parse(sessionData).getAsJsonObject();
            JsonElement openId = result.get("access_token");
            if ( null != result.get("access_token")){
                accessToken = openId.getAsString();
            }else {
                String errCode = result.get("errcode").getAsString();
                String errMsg = result.get("errmsg").getAsString();
                System.out.println("errcode: " + errCode + ", errmsg: " + errMsg);
            }
        }
    }

    public static byte[] getQRCode(String activityId, boolean isComm) {
        if( null == accessToken ) {
            setAccessToken();
        }
        if( null == accessToken ) {
            //return null;
        }
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> request = new HashMap<>();
        request.put("scene", "activity_id=" + activityId + "&is_comm=" + isComm);
        request.put("page", "pages/badge-detail/badge-detail");
        String json = new GsonBuilder().disableHtmlEscaping().create().toJson(request);
        //JsonObject jsonObject =new JsonParser().parse(json).getAsJsonObject();
        URI uri = URI.create(url);
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(uri, json, byte[].class);
    
        //根据返回值进行后续操作
        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String type = responseEntity.getHeaders().getContentType().getType();
            byte[] resultByte = responseEntity.getBody();
            if(type.equals("image")){
                return resultByte;
            }
            else if(type.equals("application")){
                String error = new String(resultByte);
                JsonParser parser =new JsonParser(); 
                JsonObject result = parser.parse(error).getAsJsonObject();
                String errCode = result.get("errcode").getAsString();
                String errMsg = result.get("errmsg").getAsString();
                System.out.println("errcode: " + errCode + ", errmsg: " + errMsg);
                if ("40001".equals(errCode) || "41001".equals(errCode) || "42001".equals(errCode)) {
                    setAccessToken();
                    return getQRCode(activityId, isComm);
                }
            }
        }
        return null;
    }
}