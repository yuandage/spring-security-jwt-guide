package yh.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class JwtTokenUtils {

	private static String KEY="yhyh";//盐
	private static long EXPIRES=360000000;//过期时间
	private static String ROLE = "role";  //角色键

	//生成token
	public static String createToken(String username, String roles) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, KEY)
				.setIssuedAt(now)
				.setSubject(username)
				.claim(ROLE, roles)
				.setExpiration(new Date(nowMillis + EXPIRES))
				.compact();
	}

	//从数据声明生成令牌
	private static String generateToken(Claims claims) {
		Date expirationDate = new Date(System.currentTimeMillis() + EXPIRES);
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, KEY).compact();
	}

	//解析token,从令牌中获取数据声明
	public static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(KEY)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	//从令牌中获取用户名
	public static String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	//获取用户所有角色
	public static String getUserRolesByToken(String token) {
		String role = (String) getClaimsFromToken(token).get(ROLE);
		return role;
	}

	//验证令牌
	public static Boolean validateToken(String username, String token) {
		String userName = getUsernameFromToken(token);
		return (userName.equals(username) && !isTokenExpired(token));
	}

	//刷新令牌
	public static String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	//判断令牌是否过期
	public static Boolean isTokenExpired(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			Date expiration = claims.getExpiration();
			return expiration.before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	//获取请求中的token
	public static String getTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String tokenHead = "Bearer ";
		if (token == null) {
			token = request.getHeader("token");
		} else if (token.contains(tokenHead)) {
			token = token.substring(tokenHead.length());
		}
		if ("".equals(token)) {
			token = null;
		}
		return token;
	}

}
