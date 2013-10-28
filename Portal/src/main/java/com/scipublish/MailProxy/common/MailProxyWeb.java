package com.scipublish.MailProxy.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-28
 * Time: PM3:09
 * To change this template use File | Settings | File Templates.
 */
public class MailProxyWeb {

    public static void outputJSONResult(String result,
                                        HttpServletResponse response) {

        try {
            response.setHeader("ContentType", "text/json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("utf-8");
            PrintWriter pw = response.getWriter();

            pw.write(result);
            pw.flush();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outputErrResult(Integer errorCode,
                                        String errorMsg,
                                        HttpServletResponse response){
        JSONObject err = new JSONObject();
        err.put("code", errorCode);
        err.put("message", errorMsg);
        outputJSONResult(err.toJSONString(), response);
    }

    public static void outputOKResult(String msg,
                                      Object result,
                                      HttpServletResponse response){
        JSONObject err = new JSONObject();
        err.put("code", 0);
        err.put("message", msg);
        err.put("result", JSON.toJSONString(result));
        outputJSONResult(err.toJSONString(), response);
    }
}
