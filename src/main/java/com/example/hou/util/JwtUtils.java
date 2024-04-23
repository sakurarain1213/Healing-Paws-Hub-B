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


//鉴权方案中 有些现成的企业级框架例如shiro
// oauth2一般是允许第三方应用代表用户获得访问权限  google facebook qq 微信登录等
//简单的鉴权方案用 jwt + springSecurity即可



@Component
public class JwtUtils {

    private static String secretKey="hsinchien";   //秘钥一定要固定且大于四位数否则会报key null错误

    private static final Integer amount = 7200;//   jwt的过期时间设置  周期/秒 默认30分钟
    //token过期时间  改成2小时 即7200s

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

        //todo 考虑如果token过期，从Redis中删除这个token

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

    //每次访问都会刷新token时效的实现：暂略
    //由于token生成时自动附带时间的随机 所以同一个token string只能有唯一过期时间不能原地更新
    //结合本简单项目的最好的方法是每个接口申请新token  以便刷新  但是需要前后端约定 改动大

    //商业化项目的实现是双Token（Access_Token,Refresh_Token）无感刷新
    //Access_Token未过期时，发送网络请求携带Access_Token即可
    //Access_Token过期后，前端携带Refresh_Token调用A接口得到新的Access_Token,把新的Access_Token替换旧的Access_Token存储起来。



}

