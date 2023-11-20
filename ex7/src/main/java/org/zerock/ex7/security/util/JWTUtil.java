package org.zerock.ex7.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Log
public class JWTUtil {
	//secretKey 길이가 32자 이상 넣을것
	private String secretKey="zerock1234567890abcdefghijklmnopqrstuvwxyzzerock1234567890abcdefghijklmnopqrstuvwxyzzerock1234567890abcdefghijklmnopqrstuvwxyz";
	private long expire=60*24*30;
	public String generateToken(String content) throws Exception{
		return Jwts.builder()
				.issuedAt(new Date())
				.expiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
				.claim("sub",content)
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8")))
				.compact();
	}

	public String validateAndExtract(String tokenStr) throws Exception{
		log.info("Jwts 클래스 : "+Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parse(tokenStr));
		Claims claims = (Claims) Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parse(tokenStr)
				.getPayload();
		return (String) claims.get("sub");
	}
}
