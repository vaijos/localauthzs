package com.membership.authzs.service;

import com.membership.authzs.model.AccessToken;

public interface AuthzService {

	public AccessToken  getAccessToken ( String jwtstr, String devicetype);
	public String  getUserAuths (String accesstoken );
}
