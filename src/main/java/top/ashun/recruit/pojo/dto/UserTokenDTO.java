package top.ashun.recruit.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.ashun.recruit.entity.UserInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 18483
 */
@Data
@ToString
@JsonIgnoreProperties(value = {"authorities"})
public class UserTokenDTO implements Serializable {
    private UserInfo userInfo;
    private List<String> roleList = new ArrayList<>();
    private List<String> permissionsList = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {

        //authorities里面就是存所有权限的权限 （但是你如果注解hasRole(就会加上前缀ROLE_ 到authorities去找的)）
        List<GrantedAuthority> list = new ArrayList<>();
        roleList.forEach(role -> {
            if (!role.startsWith("ROLE_")) {
                list.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        });
        permissionsList.forEach(permissions -> {
            list.add(new SimpleGrantedAuthority(permissions));
        });

        return list;
    }
}
