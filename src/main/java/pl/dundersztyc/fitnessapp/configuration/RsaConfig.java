package pl.dundersztyc.fitnessapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
class RsaConfig {

    @Value("${rsa.public}")
    private String rsaPublic;

    @Value("${rsa.private}")
    private String rsaPrivate;

    @Bean
    RSAPrivateKey rsaPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        rsaPrivate = rsaPrivate.replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace(" ", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivate));
        return (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
    }

    @Bean
    RSAPublicKey rsaPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        rsaPublic = rsaPublic.replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace(" ", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublic));
        return  (RSAPublicKey) kf.generatePublic(keySpecX509);
    }

}
