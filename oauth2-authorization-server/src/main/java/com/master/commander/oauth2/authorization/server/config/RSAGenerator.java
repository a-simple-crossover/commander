package com.master.commander.oauth2.authorization.server.config;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author zhangbo
 */
public class RSAGenerator {
    private static final String KEY_ID = "auth-server-kid";
    private static volatile RSAKey rsaKey;
    private static volatile JWKSet jwkSet;

    private static RSAKey generateRsaKey() {
        if (rsaKey == null) {
            synchronized (RSAGenerator.class) {
                if (rsaKey == null) {
                    try {
                        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                        keyPairGenerator.initialize(2048);
                        KeyPair keyPair = keyPairGenerator.generateKeyPair();

                        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
                        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

                        rsaKey = new RSAKey.Builder(publicKey)
                                .privateKey(privateKey)
                                .keyID(KEY_ID)
                                .algorithm(new Algorithm("RS256"))
                                .build();
                    } catch (IllegalStateException | NoSuchAlgorithmException e) {
                        throw new IllegalStateException("无法生成RSA密钥", e);
                    }
                }
            }
        }
        return rsaKey;
    }

    public static JWKSource<SecurityContext> jwkSource() {
        if (jwkSet == null) {
            synchronized (RSAGenerator.class) {
                if (jwkSet == null) {
                    RSAKey key = generateRsaKey();
                    jwkSet = new JWKSet(key);
                }
            }
        }
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
}
