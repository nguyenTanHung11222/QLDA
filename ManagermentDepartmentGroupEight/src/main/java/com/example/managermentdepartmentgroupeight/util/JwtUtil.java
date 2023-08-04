package com.example.managermentdepartmentgroupeight.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.managermentdepartmentgroupeight.authen.AccountPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import net.minidev.json.JSONObject;

@Component
public class JwtUtil {
	private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	private static final String ACCOUNT = "toan";
	private static final String SECRET = "here Mr Toan the secret length must be at least 256 bits. Please no reveal!";
	
	public Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + 864000000);
	}
	
	public String generateToken(AccountPrincipal account) {
		String token = null;
		try {
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			
			builder.claim(ACCOUNT, account);
			builder.expirationTime(generateExpirationDate());
			JWTClaimsSet claimsSet = builder.build();
			
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			JWSSigner signer = new MACSigner(SECRET.getBytes());
			signedJWT.sign(signer);
			
			token = signedJWT.serialize();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return token;
	}
	
	//--------------- getClaimsFromToken ----------------
	
		private JWTClaimsSet getClaimsFromToken(String token) {
			JWTClaimsSet claims = null;
			try {
				SignedJWT signedJWT = SignedJWT.parse(token);
				JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
				if(signedJWT.verify(verifier)) {
					claims = signedJWT.getJWTClaimsSet();
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
			}
			
			return claims;
		}
		
		//--------------- getExpirationDateFromToken ----------------
		private Date getExpirationDateFromToken(JWTClaimsSet claims) {
			return claims != null ? claims.getExpirationTime() : new Date();
		}
		
		//--------------- isTokenExpired ----------------
		private boolean isTokenExpired(JWTClaimsSet claims) {
			return getExpirationDateFromToken(claims).after(new Date());
		}
		
		
		//--------------- getAccountFromToken ----------------
		public AccountPrincipal getAccountFromToken(String token) {
			AccountPrincipal account = null;
			try {
				JWTClaimsSet claims = getClaimsFromToken(token);
				if(claims != null && isTokenExpired(claims)) {
					JSONObject jsonObject = (JSONObject) claims.getClaim(ACCOUNT);
					account = new ObjectMapper()
							.readValue(jsonObject.toJSONString(), AccountPrincipal.class);
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
			}
			
			return account;
		}
	
}
