package top.ashun.recruit.config;


import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.ashun.recruit.pojo.vo.Code;
import top.ashun.recruit.pojo.vo.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足处理类
 *
 * @author 18483
 */
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(new JsonMapper().writeValueAsString(R.failure(Code.PERMISSION_NO_ACCESS)));
        response.getWriter().flush();
    }
}
