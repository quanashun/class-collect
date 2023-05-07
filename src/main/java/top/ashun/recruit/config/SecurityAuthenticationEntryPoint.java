package top.ashun.recruit.config;


import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.ashun.recruit.pojo.vo.Code;
import top.ashun.recruit.pojo.vo.R;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 * @author 18483
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(new JsonMapper().writeValueAsString(R.failure(Code.NO_LOGIN)));
        response.getWriter().flush();
    }
}
