package com.membership.authzs.controller;

import static spark.Spark.post;

import java.util.Arrays;

import com.google.gson.Gson;
import com.membership.authzs.model.AccessToken;
import com.membership.authzs.model.AuthzRequest;
import com.membership.authzs.service.AuthzService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.authz.domain.AccessToken;
//import com.authz.service.AuthzService;
import com.membership.authzs.service.TServimpl;
import com.membership.authzs.util.JsonUtil;
import com.membership.authzs.util.SrvLog;

import spark.Request;



//@RestController
//@RequestMapping("/authz")
public class AuthzController {

    //@Autowired
    private AuthzService authzservice;
	
	public AuthzController(AuthzService tServimpl) {
		// TODO Auto-generated constructor stub
		authzservice = tServimpl;
		
	      post("/authz", (req, res) -> processATRequest(req ), JsonUtil.json());

	}

	
	private AccessToken processATRequest(Request req)
	{
		//get request params
		String paramsstr = req.body();
		SrvLog.logger.debug("Request as received:" + paramsstr);
		if (paramsstr != null)
		{

		     
		        Gson gson = new Gson();
		        try {
		        	 
		        	
		        	AuthzRequest authzrequest = gson.fromJson(paramsstr, AuthzRequest.class);
		        	//if (authzrequest.getJwt() == null) throw exception
		    		//if (authzrequest.getDevice_type() == null) throw exception
		        	System.out.println("Request jwt:" + authzrequest.getJwt()  + "device_type:" + authzrequest.getDevice_type() );
		    		//get access token using jwt and device type
		    		AccessToken accesstoken  = authzservice.getAccessToken(authzrequest.getJwt(), authzrequest.getDevice_type());

		    		return accesstoken;		            

		        } catch (Exception e) {
		            SrvLog.logger.error("getAccessToken exception:" + e.getMessage());
		            e.printStackTrace();
		            return null;
		        }			
			
		}else{
			//throw exception with error message missing request body
		}
		
		
		return null;
	}
	
	//@ResponseStatus(HttpStatus.OK)
    //@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})//, RequestMethod.GET
	//@RequestMapping(method = RequestMethod.GET )
	//@RequestParam(value = "jwt", required = false)  @RequestParam(value = "device_type", required = false) 
	public AccessToken  getAccessToken (String jwtstr, String devicetype){

		System.out.println("Request jwt:" + jwtstr + "device_type:" + devicetype);
		//get access token using jwt and device type
		AccessToken accesstoken  = authzservice.getAccessToken(jwtstr, devicetype);

		return accesstoken;
	}
	//define methods
	

}
