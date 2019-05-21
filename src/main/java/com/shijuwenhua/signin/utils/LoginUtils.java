package com.shijuwenhua.signin.utils;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
 
public class LoginUtils{
 
    private static final long serialVersionUID = 1L;

    private static final String APPID = "wxfdef7ebe2a985224";  
    private static final String SECRET = "3ce63e12fcc44c8b73e54ccffd98e0bb";

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
    //  public static void main(String arg[]){
    //     String o = getOpenId("aaa");
    //     System.out.println(o);
    //  }
}