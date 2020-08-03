package com.myapp.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {
	
	private static final String SECRET_SIGNATURE = "keysecret123456";
	private static Algorithm algorithmInstance;
	private static JWTVerifier verifierInstance;
	
	private Algorithm getAlgorithm() {
		if(algorithmInstance == null) {
			try {
				algorithmInstance = Algorithm.HMAC256(SECRET_SIGNATURE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return algorithmInstance;
	}
	
	private JWTVerifier getVerifier() {
		try {
			if(verifierInstance == null) {
				verifierInstance = JWT.require(getAlgorithm())
			        .withIssuer("auth0")
			        .build();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return verifierInstance;
	}
	
	public String createToken() {
		String token = null;
		
		try {
		    Algorithm algorithm = getAlgorithm();
		    token = JWT.create()
		        .withIssuer("TokenUtilGenerator")
		        .sign(algorithm);
		} catch (Exception e){
		    e.printStackTrace();
		}
		
		return token;
	}
	
	public DecodedJWT decodeToken(String token) {
		DecodedJWT jwt = null;
		try {
		    jwt = getVerifier().verify(token);
		} catch (Exception e){
		    e.printStackTrace();
		}
		return jwt;
	}
}
