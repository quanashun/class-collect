package top.ashun.recruit.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import top.ashun.recruit.entity.UserInfo;
import top.ashun.recruit.pojo.dto.UserTokenDTO;

import java.util.List;


/**
 * 获取当前登录用户的一些信息
 */
public class RequestUtil {
    public static UserInfo getLoginUser() {
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static UserTokenDTO getLoginUserTokenDTO() {
        return (UserTokenDTO) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }


    public static List<String> getRoleList() {
        return getLoginUserTokenDTO().getRoleList();
    }


    public static String getId() {
        return getLoginUser().getId();
    }

    public static void setUserTokenDTO(UserTokenDTO userTokenDTO) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userTokenDTO.getUserInfo(), userTokenDTO, userTokenDTO.getAuthorities()));
    }

}
