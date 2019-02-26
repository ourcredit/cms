package com.monkey.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monkey.common.wechatsdk.HttpUtil;
import com.monkey.common.wechatsdk.PayConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QyChatHelper {

    /// <summary>
    /// 获取机构人员
    /// </summary>
    /// <returns></returns>
    public static List<JSONObject> getUserList() {
        Object t= CacheManager.get(PayConfig.ACCESSTOKENKEY);
        String token="";
        if(t==null){
            token=GetAccessToken();
        }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";
        String result = HttpUtil.sendGet(url, "access_token=" + token + "&department_id=1&fetch_child=1&status=0");
        JSONObject json = JSONObject.parseObject(result);
        if (json.get("userlist") != null) {
            Object ticket = json.get("userlist");
            return (List<JSONObject>) ticket;
        } else {
            return null;
        }
    }

    public static void SendMessage(String userId) {
       Object tt= CacheManager.get(PayConfig.ACCESSTOKENKEY);
       String token="";
       if(tt==null){
           token=GetAccessToken();
       }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token;
        Map<String, Object> d = new HashMap<>();
        d.put("touser", userId);
        d.put("msgtype", "text");
        d.put("agentid", "1000002");
        Map<String, Object> t = new HashMap<>();
        t.put("content", "您有新的烘干订单,请注意查收");
        d.put("text", t);
        HttpUtil.postData(url, JSON.toJSONString(d));
    }
    /// <summary>
    /// 获取accesstoken
    /// </summary>
    /// <returns></returns>
    public static String GetAccessToken() {
        String result = HttpUtil.sendGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken", "corpid=" + PayConfig.QY_CORPID + "&corpsecret=" + PayConfig.QY_SECRET);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(result);
        //获取会话密钥（session_key）
        String session_key = json.get("access_token").toString();
        CacheManager.put(PayConfig.ACCESSTOKENKEY,session_key,60*60*2);
        return session_key;
    }
}
