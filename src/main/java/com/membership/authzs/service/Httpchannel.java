package com.membership.authzs.service;



import com.google.gson.*;
import java.util.Map;
import com.google.gson.internal.LinkedTreeMap;
import com.membership.authzs.util.SrvLog;

/**
 * Created by joshiv 
 */


 public class Httpchannel {
    //abstract public void test();

    protected String  doSecurePost(String headerJson, String url, String body ) throws Exception{
        HttpClientHelper helper = HttpClientHelper.getInstance();
        String resp = helper.doPost(headerJson, url, body);
        System.out.println(url +":\n" + resp);
        return resp;
    }

    public void debug ( LinkedTreeMap<String,Object> resp ){
        for (Map.Entry<String, Object> entry : resp.entrySet()) {
            SrvLog.logger.info( entry.getKey() + "<=>"  + entry.getValue());
        }

    }
    public String ERROR_GENERAL_MSG( LinkedTreeMap<String,Object> resp ){
        return (String) resp.get("ERROR_GENERAL_MSG");
    }
    public String ERROR_CODE( LinkedTreeMap<String,Object> resp ){
        return (String) resp.get("ERROR_CODE");
    }
    public String STATUS_CODE( LinkedTreeMap<String,Object> resp ){
        return (String) resp.get("STATUS_CODE");
    }
    public String RESPONSE_NAME( LinkedTreeMap<String,Object> resp ){
        return (String) resp.get("RESPONSE_NAME");
    }

}
