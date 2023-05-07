package top.ashun.recruit.pojo.vo;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 18483
 */
@Data
public class UserPasswordLoginVO {
    @NotNull(message = "登陆账号不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "验证码Id不能为空")
    private String captchaId;
    @NotNull(message = "验证码不能为空")
    private String captcha;
}