package com.xxxx.server.config.security.component;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Z
 * @title: JwtTokenUtils
 * @projectName yeb
 * @description: TODO
 * @date 2022/4/2919:52
 */
@SuppressWarnings({"all"})
@Component
public class JwtTokenUtils {
    private static final String CLAIM_KET_USERNAME = "sub";
    private static final String CLAIM_KET_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


//根据用户名生成ToKen
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KET_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KET_CREATED,new Date());


        return  generateToken(claims);

    }
//根据荷载生成jwt  token
    private  String generateToken( Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 从Token中获取Token
     * @param token
     * @return
     */
    public String getUserNameFromTokem(String token){
        String username;
        try {
            Claims claims=getClaimsFormToken(token);
            username =claims.getSubject();
        } catch (Exception e) {
            username=null;
        }
        return username;
    }

    /**
     *
     *  验证token 是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){
        String username = getUserNameFromTokem(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断是否可以刷新token
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    public String refreshToken(String token){
        Claims claims=getClaimsFormToken(token);
        claims.put(CLAIM_KET_CREATED,new Date());
        return  generateToken(claims);
    }


    /**
     * 判断其是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate= getExpiredDateFromToken(token);
        return  expireDate.before(new Date());

    }


    /**
     * 从Token中获取过期时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims= getClaimsFormToken(token);
        return  claims.getExpiration();

    }





    /**
     * 从Token中获取荷载
     * @param token
     * @return
     */
    private Claims getClaimsFormToken(String token) {
        Claims claims=null;
        try {
            claims = Jwts.parser()
                   .setSigningKey(secret)
                   .parseClaimsJws(token)
                   .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  claims;
    }


    /**
     *  生成token失效时间
     * @return
     */
    private Date generateExpirationDate() {
            return new Date(System.currentTimeMillis()+expiration*1000);
    }


}
