package com.membership.authzs.model;

//import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;


public class AccessToken {
	
	//@JsonProperty("ERROR_GENERAL_MSG")
	String ERROR_GENERAL_MSG ;//error_general_msg;//ERROR_GENERAL_MSG

	//@JsonProperty("ERROR_CODE")
	int ERROR_CODE;//error_code; //ERROR_CODE

	//@JsonProperty("STATUS_CODE")
	int STATUS_CODE;//status_code; //STATUS_CODE
	
	//@JsonProperty("STATUS_DESCRIPTION")
	String STATUS_DESCRIPTION;//status_description;//STATUS_DESCRIPTION
	
	//@JsonProperty("RESPONSE_VERSION")
	String RESPONSE_VERSION;//response_version; //"RESPONSE_VERSION": "1.1",
	
	//@JsonProperty("RESPONSE_NAME")
	String RESPONSE_NAME;//response_name ;//"RESPONSE_NAME": "MEMBER_AUTHZ_RESP",
	
	//@JsonProperty("access_token")
	String access_token; //"access_token"


	public String getERROR_GENERAL_MSG() {
		return ERROR_GENERAL_MSG;
	}
	public void setERROR_GENERAL_MSG(String eRROR_GENERAL_MSG) {
		ERROR_GENERAL_MSG = eRROR_GENERAL_MSG;
	}
	public int getERROR_CODE() {
		return ERROR_CODE;
	}
	public void setERROR_CODE(int eRROR_CODE) {
		ERROR_CODE = eRROR_CODE;
	}
	public int getSTATUS_CODE() {
		return STATUS_CODE;
	}
	public void setSTATUS_CODE(int sTATUS_CODE) {
		STATUS_CODE = sTATUS_CODE;
	}
	public String getSTATUS_DESCRIPTION() {
		return STATUS_DESCRIPTION;
	}
	public void setSTATUS_DESCRIPTION(String sTATUS_DESCRIPTION) {
		STATUS_DESCRIPTION = sTATUS_DESCRIPTION;
	}
	public String getRESPONSE_VERSION() {
		return RESPONSE_VERSION;
	}
	public void setRESPONSE_VERSION(String rESPONSE_VERSION) {
		RESPONSE_VERSION = rESPONSE_VERSION;
	}
	public String getRESPONSE_NAME() {
		return RESPONSE_NAME;
	}
	public void setRESPONSE_NAME(String rESPONSE_NAME) {
		RESPONSE_NAME = rESPONSE_NAME;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
/*
	@JsonCreator
	public static AccessToken Create(String jsonString)
	{
		ObjectMapper mapper =new ObjectMapper();
		AccessToken pc = null;
	    try {
	        pc = mapper.readValue(jsonString, AccessToken.class);
//	    } catch (JsonParseException | JsonMappingException | IOException e) {
//	        // handle
//	    	e.printStackTrace();
//	    }
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    return pc;
	}
*/	
}
