package com.shijuwenhua.signin.model;
import lombok.Data;

@Data public class WeChatSession{

    String openid;
    String session_key;
    String unionid;
    String errcode;
    String errmsg;
}