package com.xqh.test.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import com.xqh.utils.DateUtil;
import com.xqh.utils.EncryptUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JWTTest
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/6/26 14:16
 * @Version 1.0
 */
@Slf4j
public class JWTTest {

    public static void main(String[] args) {
        String secret = "dddsecret";
        System.out.println(getJwtToken("appkeylll", secret));
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdXRoIHRva2VuIiwiYXVkIjoiYXBwIiwiaXNzIjoiaG90ZWwtc2VydmljZSIsImFwcEtleSI6ImFwcGtleWxsbCIsImV4cCI6MTU2MTUzMTI0NSwiaWF0IjoxNTYxNTMxMTg1fQ.VXlGE8CDLWPULrtUCre3uZtQEFM53rcO1jrEve46N7k";
//        verfierJwt(token, secret);
        System.out.println(EncryptUtils.base64Decode(token));
    }

    /**
     * 获取 Jwt请求头
     * @return
     */
    public static Map<String, Object> getJwtHead(){
        Map<String, Object> map = Maps.newHashMap();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return map;
    }

    public static String getJwtToken(String appKey, String secret){
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create().withHeader(getJwtHead())
                .withClaim("appKey", appKey)
                .withIssuer("hotel-service")
                .withSubject("auth token")
                .withAudience("app") // 签名的观众 可以理解为 谁接收签名
                .withIssuedAt(now) // 生成签名时间
                .withExpiresAt(DateUtil.plusDate(now, 1, ChronoUnit.MINUTES)) // 有效期30分钟
                .sign(algorithm);
        return token;
    }

    public static boolean verfierJwt(String token, String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
//                .withIssuer("hotel-service")
                .build();
        DecodedJWT jwt = verifier.verify(token);


        // 可以从 token中获取 签名的主题, 观众 和自主定义的声明信息.
        String subjet = jwt.getSubject();
        List<String> audience = jwt.getAudience();
        Map<String, Claim> claims = jwt.getClaims();
        log.info("subjet ==>{}, audience ==>{}, claims ==>{}", subjet, JSON.toJSONString(audience), JSON.toJSONString(claims));

        return true;
    }
}
