package com.kamal.util;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class JwtSecretKeyGenerator {

    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);

        String secretKey = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("Generated Secret Key: " + secretKey);
    }
}
