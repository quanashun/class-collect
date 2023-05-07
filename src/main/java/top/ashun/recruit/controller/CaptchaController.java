package top.ashun.recruit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ashun.recruit.config.BusinessException;
import top.ashun.recruit.pojo.vo.Code;
import top.ashun.recruit.pojo.vo.R;
import top.ashun.recruit.util.captcha.ImageCaptchaUtil;

/**
 * @author 18483
 */
@RestController
@RequestMapping("/captcha")
@Api(tags = "验证码")
public class CaptchaController {
    @Autowired
    private ImageCaptchaUtil imageCaptchaUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/getImageCaptcha")
    @ApiOperation("获取图像验证码")
    public R getImageCaptcha() {
        return imageCaptchaUtil.getCaptcha();
    }

    @GetMapping("/verifyImageCaptcha")
    @ApiOperation("验证图像验证码")
    public R verifyImageCaptcha(String captchaId, String captcha) {
        // 获取验证码与redis中存储验证码进行比较
        String redisCaptcha = (String) redisTemplate.opsForValue().get(captchaId);
        if (redisCaptcha == null) {
            // 验证码失效
            BusinessException.error(Code.USER_CAPTCHA_EXPIRE);
            return null;
        }
        if (redisCaptcha.equals(captcha)) {
            // 验证码匹配成功
            redisTemplate.delete(captchaId);
            return R.success(true);
        } else {
            // 验证码匹配失败
            BusinessException.error(Code.USER_CAPTCHA_ERROR);
            return null;
        }
    }
}
