package top.ashun.recruit.pojo.vo;

import lombok.Data;
import top.ashun.recruit.entity.UserInfo;

import java.util.List;

/**
 * @author 18483
 */
@Data
public class UserLoginRespVO {
    private UserInfo userInfo;
    private String token;
    private List<String> roleList;

}