package top.ashun.recruit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.ashun.recruit.config.filter.HttpSecurityFilter;

/**
 * @author 18483
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private HttpSecurityFilter httpSecurityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // 添加 CORS 配置信息
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        // 配置 HttpSecurity
        httpSecurity
                .cors()
                .configurationSource(source)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .antMatchers("/logins/**", "/registers/**","/captcha/***", "/test/**").permitAll()
                .antMatchers("/swagger-ui/*", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .disable()
                .formLogin()
                .disable()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterBefore(httpSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

        return httpSecurity.build();
    }

    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new SecurityAuthenticationEntryPoint();
    }

    public AccessDeniedHandler accessDeniedHandler() {
        return new SecurityAccessDeniedHandler();
    }
}

