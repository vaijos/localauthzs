package com.membership.authzs.service;

/**
 * Created by joshiv
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.membership.authzs.App;
import com.membership.authzs.util.SrvLog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;

public class HttpClientHelper
{
    private static HttpClientHelper m_instance = null;
    private  CloseableHttpClient m_httpClient = null;
    public static HttpClientHelper getInstance(){
        if (m_instance == null){
            try {
                m_instance = new HttpClientHelper(true);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return m_instance;
    }
    public HttpClientHelper(boolean secured) throws  Exception{
        if (secured) {
            // Trust own CA and all self-signed certs

            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new File( App.getProperty("QA_KEYSTORE") ), null,
                            new TrustSelfSignedStrategy())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            m_httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
        }
        else{
            m_httpClient = HttpClients.createDefault();
        }

    }
    public String doPost(String jsonStr, String url, String body) {
        Map<String, String > map =
        new Gson().fromJson(jsonStr, new TypeToken<HashMap<String, String>>(){}.getType());
        return doPost(map, url, body);
    }

    public String doGet(Map<String, String> headers, String url){
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse resp = null;
        HttpEntity entity = null;
        try {
            if(headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader( entry.getKey(), entry.getValue());
                }
            }

            resp = m_httpClient.execute(httpGet);
            System.out.println( "########status:" +  resp.getStatusLine());
            entity = resp.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((resp.getEntity().getContent())));

            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            EntityUtils.consume(entity);
            return sb.toString();
        }catch (Exception e){
            SrvLog.logger.error(e.getMessage());
        }finally {
            try {
                resp.close();
            }catch(Exception e)
            {}
        }
        return null;

    }

    public String doPost(Map<String, String> headers, String url, String body) {

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse resp = null;
        HttpEntity entity = null;
        try {
            StringEntity bodyEntity = new StringEntity(body);
            httpPost.setEntity(bodyEntity);
            if(headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader( entry.getKey(), entry.getValue());
                }
            }
            resp = m_httpClient.execute(httpPost);
            System.out.println( "########status:" +  resp.getStatusLine());
            entity = resp.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((resp.getEntity().getContent())));

            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            EntityUtils.consume(entity);
            return sb.toString();
        }catch (Exception e){
            SrvLog.logger.error(e.getMessage());
        }finally {
            try {
                resp.close();
            }catch(Exception e)
            {}
        }
        return null;
    }
    private UrlEncodedFormEntity addHeaders( Map<String, String> headers )
            throws java.io.UnsupportedEncodingException{
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            nvps.add(new BasicNameValuePair(  entry.getKey(), entry.getValue()));
        }
        return new UrlEncodedFormEntity(nvps);
    }

}
