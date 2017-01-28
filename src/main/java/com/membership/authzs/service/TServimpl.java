package com.membership.authzs.service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.impl.client.HttpClients;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.membership.authzs.model.AccessToken;
import com.membership.authzs.util.SrvLog;
import com.membership.authzs.App;

public class TServimpl implements AuthzService{

	
	String authzurl;
	//String authzsrv;
	//public static String final AUTHZ_REQUEST = 
	//ObjectMapper mapper ;//= new ObjectMapper();
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//get params for service call.
		//	getjwt
		//	gettype
		//make autz service call authz(jwt, type) : response
		//get and  return accesstoken from response.
		//QS. get structions for response, accesstoke and type

//////////////can go in config.		
		//Initilise Rest Template with converters
		
		//set up URL
		//set up header map
		//set up body map

		//test jSON parsing 
		String teststr = "{\"ERROR_GENERAL_MSG\": \"\", \"ERROR_CODE\": \"0\", \"STATUS_CODE\": \"0\", \"STATUS_DESCRIPTION\": \"\", \"RESPONSE_VERSION\": \"1.1\", \"RESPONSE_NAME\": \"MEMBER_AUTHZ_RESP\", \"access_token\": \"27139XxX-JUYTIOBUG42DOMRYGUXVAQTTONBUGNTCJ5YEO2SEONMUYOLTOQZTIOCZNFEDEOCXMVZWWSZLJM3EQOBZJZ3TIYKQPBZC63JPKNWTCRDXF5SUQUTMKVVGSQ2XINJU44KHIZVHM53YMVUXGVKQGAXUINSCMRFDK6JWKBMUQ42RINKGETDHHBLFIT22N5BEKOBRG5QTOL2RJVCTCWTUOBMXIUCTOV2UC4SXI4\"}";
		//String teststr = "{ERROR_GENERAL_MSG: , ERROR_CODE: 0, STATUS_CODE: 0, STATUS_DESCRIPTION: , RESPONSE_VERSION: 1.1, RESPONSE_NAME: MEMBER_AUTHZ_RESP, access_token: 27139XxX-JUYTIOBUG42DOMRYGUXVAQTTONBUGNTCJ5YEO2SEONMUYOLTOQZTIOCZNFEDEOCXMVZWWSZLJM3EQOBZJZ3TIYKQPBZC63JPKNWTCRDXF5SUQUTMKVVGSQ2XINJU44KHIZVHM53YMVUXGVKQGAXUINSCMRFDK6JWKBMUQ42RINKGETDHHBLFIT22N5BEKOBRG5QTOL2RJVCTCWTUOBMXIUCTOV2UC4SXI4}";
		//AccessToken taccesstoken1 = AccessToken.Create(teststr);

		String url ="https://sessionapi.stg.dowjones.net:8443/authorization/membership/v2/authz";

		String tempjwtstr = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJvSXRzeVBDVGhpSVZHVmNraFFxRHl2Y3MiLCJzdWIiOiJFNk9PMkhWQ1FIVlBFRExFUFhIRTJYRTJJTSIsImxvZ291dF9vbGRlc3Rfc2Vzc2lvbiI6IlkiLCJpYXQiOjE0ODM1NTc3MjUsImlzcyI6Imh0dHBzOi8vc3NvdGVuMS5pbnQuYWNjb3VudHMuZG93am9uZXMuY29tLyIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSJ9.s5mlr88mFLCKIcNf4rtpge8GtoYLLrupSteYWWx5eIU";        
		String devicetype = "M";
		TServimpl tobj = new TServimpl();
		App.init();
		//get access token using jwt and device type
		AccessToken accesstoken  = tobj.getAccessToken(tempjwtstr, devicetype);
		
        System.out.println("Access Token" + accesstoken);
        
        //get user auth matrix OR list of auths 
        String userauths  = tobj.getUserAuths(accesstoken.getAccess_token());
        
        
//////////////////////////
		
		
	}

	
	//AccessToken
	public AccessToken  getAccessToken (String jwtstr, String devicetype)
	{
        //Body
		Map<String, String> bodyMap = new HashMap<String, String>();

		bodyMap.put("jwt", jwtstr);
		bodyMap.put("device_type", devicetype);
		
		Map<String, String> headerMap = new HashMap<String, String>();
        
		String result = channelpost(this.getAuthzurl(),headerMap ,bodyMap);
		
		AccessToken accesstoken = new AccessToken();//AccessToken.Create(result);
		
        Gson gson = new Gson();
        //LinkedTreeMap<String,Object> somap=new LinkedTreeMap<String,Object>();
        accesstoken =  gson.fromJson(result,  accesstoken.getClass());

		//AccessToken accesstoken =  JSON.parse();
		//AccessToken accesstoken = mapper.convertValue(result, AccessToken.class);
//		AccessToken accesstoken=null;
//		try {
//			accesstoken = mapper.readValue(result, AccessToken.class);
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//convert string to access token
		return accesstoken;
	}

/*	public String  getAccessTokenstr (String jwtstr, String devicetype)
	{
        //Body
		Map<String, String> bodyMap = new HashMap<String, String>();

		bodyMap.put("jwt", jwtstr);
		bodyMap.put("device_type", devicetype);
        
		String result = channelpost(this.getAuthzurl(),null ,bodyMap);
		
		return result;

	}*/
	
	//AccessToken
	public String  getUserAuths (String accesstoken )
	{
		String url ="https://sessionapi.stg.dowjones.net:8443/authorization/MSRVS_AUTH_GET_USER_AUTHS";
		//Body
		Map<String, String> bodyMap = new HashMap<String, String>();
		Map<String, String> headerMap = new HashMap<String, String>();
		//would need to make Accesstoken object and extract token string from it.
		headerMap.put("FSS_SessionID", "TODO");
		
		//put accesstoken in header and body goes empty
		//TODO check headermap is updnded
		String result = channelpost(url,headerMap ,bodyMap);
		
		return result;
	}	

	//httpChannel.post
	public static String channelpost(String url, Map headermap, Map bodyMap)
	{

	     Map<String, String> defaultHeader = new HashMap<String, String>();
	        defaultHeader.put("Content-Type", "application/json");
	        //defaultHeader.put("FSS_PRODUCT_ID", "16");
	        defaultHeader.putAll(headermap);

	        try {
	            String headerJson = new Gson().toJson( defaultHeader);
	            //String url = String.format("%s/IOTA/IOTA_CREATE_HS256_JWT_ID_TOKEN", App.getProperty("MBRSHP_SVR"));
	            String body = new Gson().toJson(bodyMap);
	            Httpchannel httpchannel = new Httpchannel(); 
	            String resp = httpchannel.doSecurePost(  headerJson, url, body);
	            return resp;
	        }catch ( Exception e){
	            SrvLog.logger.debug("getGroupInfo error:" +  e.getMessage());
	        }
	        return null;		

	}


	///////////////////////////////////
	
	public String getAuthzurl() {
		
		if (authzurl ==null)
			authzurl = App.getProperty("MBRSHP_SVR");
		return authzurl;
	}

	public void setAuthzurl(String authzurl) {
		this.authzurl = authzurl;
	}


	
}
