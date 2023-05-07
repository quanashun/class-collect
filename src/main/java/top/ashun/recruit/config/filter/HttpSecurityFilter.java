package top.ashun.recruit.config.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.ashun.recruit.pojo.dto.UserTokenDTO;
import top.ashun.recruit.service.RedisService;
import top.ashun.recruit.service.UserServiceImpl;
import top.ashun.recruit.util.RequestUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author 18483
 */
//在请求处理前验证token
@Component
@Slf4j
public class HttpSecurityFilter extends OncePerRequestFilter {
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserServiceImpl userService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 在这里获取token的用户和权限
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            UserTokenDTO userTokenDTO;
            try {
                userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (userTokenDTO != null) {
                log.info(
                        "user id : {} , uri : {} , method : {} , params : {}",
                        userTokenDTO.getUserInfo().getId(),
                        httpServletRequest.getRequestURI(),
                        httpServletRequest.getMethod(),
                        new ObjectMapper().writeValueAsString(httpServletRequest.getParameterMap())
                );
                RequestUtil.setUserTokenDTO(userTokenDTO);
            }
        } else {
            // 创建匿名身份验证令牌
            Authentication anonymousAuth = new UsernamePasswordAuthenticationToken("anonymousUser", "anonymousUser",
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
            // 将匿名身份验证令牌设置为当前身份验证
            SecurityContextHolder.getContext().setAuthentication(anonymousAuth);
        }
//        httpServletResponse.setHeader("cross-");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
