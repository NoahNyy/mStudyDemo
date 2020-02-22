package org.noahnyy.demo.spring.security.util;

import org.noahnyy.demo.spring.security.config.JwtAudienceConfig;
import org.springframework.util.Base64Utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author niuyy
 * @since 2020/2/18
 */
@Slf4j
public class JwtTokenUtil {

    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 解析jwt
     *
     * @param token        token
     * @param base64Secret 加密字符串
     * @return 签证
     */
    public static Claims parseJWT(String token, String base64Secret) {
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.warn("===== Token过期 =====", e);
            throw e;
        } catch (Exception e) {
            log.error("===== Token解析异常 =====", e);
            throw e;
        }
    }


    /**
     * 构建jwt
     *
     * @param userId         userId
     * @param username       username
     * @param audienceConfig audienceConfig
     * @return token
     */
    public static String createToken(String userId, String username, JwtAudienceConfig audienceConfig) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(audienceConfig.getBase64Secret());
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            //userId是重要信息，进行加密下
            String encryUserId = Base64Utils.encodeToString(userId.getBytes());
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("userId", encryUserId)
                    // 代表这个JWT的主体，即它的所有人
                    .setSubject(username)
                    // 代表这个JWT的签发主体；
                    .setIssuer(audienceConfig.getClientId())
                    // 是一个时间戳，代表这个JWT的签发时间；
                    .setIssuedAt(new Date())
                    // 代表这个JWT的接收对象；
                    .setAudience(audienceConfig.getName())
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            if (audienceConfig.getExpiresSecond() >= 0) {
                long expMillis = nowMillis + audienceConfig.getExpiresSecond();
                Date exp = new Date(expMillis);
                // 是一个时间戳，代表这个JWT的过期时间；
                builder.setExpiration(exp)
                        // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
                        .setNotBefore(now);
            }
            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.warn("======= token 签名失败 =======", e);
            throw e;
        }
    }

    /**
     * 从token中获取用户名
     *
     * @param token        token
     * @param base64Secret base64Secret
     * @return username
     */
    public static String getUsername(String token, String base64Secret) {
        return parseJWT(token, base64Secret).getSubject();
    }

    /**
     * 从token中获取用户ID
     *
     * @param token        token
     * @param base64Secret base64Secret
     * @return userId
     */
    public static String getUserId(String token, String base64Secret) {
        String userId = parseJWT(token, base64Secret).get("userId", String.class);
        return new String(Base64Utils.decodeFromString(userId));
    }

    /**
     * 是否已过期
     *
     * @param token        token
     * @param base64Secret base64Secret
     * @return true-过期
     */
    public static boolean isExpiration(String token, String base64Secret) {
        return parseJWT(token, base64Secret).getExpiration().before(new Date());
    }

}
