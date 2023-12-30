package com.example.hou.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtils {

    private static String secretKey="hsinchien";   //秘钥一定要固定且大于四位数否则会报key null错误

    private static Integer amount = 1800;//   jwt的过期时间设置  周期/秒 默认30分钟


    // @Value("${jwt.secretKey}")   加了这行会报错
    //实现依赖注入需要属性具有set方法
    public void secretKey(String secretKey) {
        JwtUtils.secretKey =  secretKey;
    }


    /**
     * 创建token
     * @param payloadMap 存储的内容，自定义，一般是用户id
     * @return
     */

    public static String generateToken(Map<String, String> payloadMap) {

        HashMap headers = new HashMap();

        JWTCreator.Builder builder = JWT.create();

        //定义jwt过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, amount);


        //payload
        payloadMap.forEach((k, v) ->{
            builder.withClaim(k, v);
        });


        // 生成token
        String token = builder.withHeader(headers)//header
                //.withClaim("second",amount)//jwt的过期周期/秒，可以用于jwt快过期的时候自动刷新
                .withExpiresAt(instance.getTime())//指定令牌的过期时间
                .sign(Algorithm.HMAC256(secretKey));//签名


        return token;
    }


    /**
     * 校验token是否合法
     * @param token
     * @return
     */
    public static DecodedJWT verifyToken(String token) {

        /*
        如果有任何验证异常，此处都会抛出异常
        SignatureVerificationException 签名不一致异常
        TokenExpiredException 令牌过期异常
        AlgorithmMismatchException 算法不匹配异常
        InvalidClaimException 失效的payload异常
        */
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);



        return decodedJWT;
    }

    /**
     * 获取token信息
     * @param token
     * @return
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
        return decodedJWT;
    }

    /**
     * 获取token信息方法
     */
    /*public static Map<String, Claim> getTokenInfo(String token) {

        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token).getClaims();
    }*/
}

