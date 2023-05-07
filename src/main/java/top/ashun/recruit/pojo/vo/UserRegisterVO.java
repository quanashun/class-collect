package top.ashun.recruit.pojo.vo;

import lombok.Data;

/**
 * @author 18483
 */
@Data
public class UserRegisterVO {
    private String username;
    private String phone;
    private String password;
    private String captchaId;
    private String captcha;
}
