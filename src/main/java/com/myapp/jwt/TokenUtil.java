package com.myapp.jwt;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtil {

	private static final String SECRET_SIGNATURE = "keysecret123456";
	public static final String issuer = "TokenUtil"; // entità che ha generato il token
	static final String CLAIM_KEY_CREATED = "iat";
	private static final Long expiration = (long) (1000*60*60);
	
	public static final String tokenHeader = "x-auth-token";

	public String generateToken(Claims claims, String issuer) {
        if(issuer == null)
        	issuer = issuer;
		
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.setIssuer(issuer)
				.signWith(SignatureAlgorithm.HS256, SECRET_SIGNATURE).compact();
	}
	
	public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_SIGNATURE)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
	
	public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims, claims.getIssuer());
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
	
	private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
	
	public Boolean canTokenBeRefreshed(String token) {
        final Date created = getCreatedDateFromToken(token);
        return  (!isTokenExpired(token));
    }
	
	public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if(expiration == null) return true;
        
        return expiration.before(new Date());
    }
	
	 public Date getExpirationDateFromToken(String token) {
	        Date expiration;
	        try {
	            final Claims claims = getClaimsFromToken(token);
	            expiration = claims.getExpiration();
	        } catch (Exception e) {
	            expiration = null;
	        }
	        return expiration;
	    }
	
	public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }
	
	public String getToken(HttpServletRequest request) {
		Cookie[] cookie = request.getCookies();
		for(Cookie c : cookie) {
			if(c.getName().equals(tokenHeader))
				return c.getValue();
		}
		
		return null;
	}
}
