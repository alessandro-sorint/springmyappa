package com.myapp;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.myapp.jwt.TokenUtil;

public class MyTokenTestApp {
	public static void main(String[] args) {
	   TokenUtil util = new TokenUtil();
	   String token = util.createToken("nicola");
	   System.out.println("token: " + token);
	   
	   DecodedJWT dec = util.decodeToken(token);
	   System.out.println("dec: " + dec.getIssuer() + " " + dec.getId() + " " + dec.getSignature() + " " + dec.getClaim("username").asString() + " " + dec.getClaim("provaKey").asString());
	}
}
