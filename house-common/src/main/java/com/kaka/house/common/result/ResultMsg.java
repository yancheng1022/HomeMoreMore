package com.kaka.house.common.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Author yc
 * @Date 2018/11/8 14:51
 * 返回结果 快速构建工具类
 */
public class ResultMsg {
    public static final String errorMsgKey = "errorMsg";
    public static final String successMsgKey = "successMsg";

    private String errorMsg;

    private String successMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public static ResultMsg errorMsg(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }

    public static ResultMsg successMsg(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }

    //判断是否成功
    public boolean isSuccess(){
        return errorMsg == null;
    }


    public Map<String,String> asMap(){
        Map<String,String> map = Maps.newHashMap();
        map.put(successMsgKey, successMsg);
        map.put(errorMsgKey, errorMsg);
        return map;
    }

    //将返回结果序列化为url
    public String asUrlParams(){
        Map<String, String> map = asMap();
        Map<String, String> newMap = Maps.newHashMap();
        map.forEach((k,v) -> {if(v!=null)
            try {
                newMap.put(k, URLEncoder.encode(v,"utf-8"));
            } catch (UnsupportedEncodingException e) {

            }});
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }
}
