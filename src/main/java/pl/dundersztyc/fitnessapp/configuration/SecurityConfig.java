package pl.dundersztyc.fitnessapp.configuration;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private RSAPrivateKey rsaPrivateKey;

    @Autowired
    private RSAPublicKey rsaPublicKey;
}
