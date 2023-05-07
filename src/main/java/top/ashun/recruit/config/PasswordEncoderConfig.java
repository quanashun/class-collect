package top.ashun.recruit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 18483
 */
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        //默认加密方式
        return  new BCryptPasswordEncoder();
    }
}
