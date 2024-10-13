package com.example.hotel_projects.utl;

import com.example.hotel_projects.dto.JwtDTO;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.enums.ProfileRole;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


public class JWTUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24;

    private static final String secretKey = "fhifjhffnrueialrewrrrjfsruytrettdft46uhgyihkhgkgfftrmhgfffrtrmyukillkjkj";

    public static String encode(String email, ProfileRole role, AppLanguage appLanguage) {

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        // Token yaratish jarayoni
        return Jwts.builder()
                .setIssuedAt(new Date())
                .signWith(secretKeySpec, sa)
                .claim("email", email)
                .claim("role", role)
                .claim("appLanguage", appLanguage)
                .setExpiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .compact();
    }


    public static JwtDTO decode(String token) {

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getBody();

        String email = claims.get("email", String.class);
        String role = claims.get("role", String.class);
        String appLanguage = claims.get("appLanguage", String.class);

        AppLanguage appLanguageEnum = AppLanguage.valueOf(appLanguage);
        ProfileRole profileRoleEnum = ProfileRole.valueOf(role);

            return new JwtDTO(email, profileRoleEnum, appLanguageEnum);

    }
}


