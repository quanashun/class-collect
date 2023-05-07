package top.ashun.recruit.util.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;
import top.ashun.recruit.config.BusinessException;
import top.ashun.recruit.pojo.vo.Code;
import top.ashun.recruit.pojo.vo.R;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author 18483
 * 图像验证码工具类
 */
@Component
public class ImageCaptchaUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${captchaTimeout: 5}")
    private int captchaTimeout;
    @Value("${isOpenCaptcha: false}")
    private boolean isOpenCaptcha;

    public DefaultKaptcha getDefaultKaptcha() {
        //验证码生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        //配置
        Properties properties = new Properties();
        //是否有边框
        properties.setProperty("kaptcha.border", "yes");
        //设置边框颜色
//        properties.setProperty("kaptcha.border.color", "blue");
        //边框粗细度，默认为1
//         properties.setProperty("kaptcha.border.thickness","1");
        //验证码
        properties.setProperty("kaptcha.session.key", "code");
        //验证码文本字符颜色 默认为黑色
//        properties.setProperty("kaptcha.textproducer.font.color", "black");
        //设置字体样式
//        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅 黑");
        //字体大小，默认40
        properties.setProperty("kaptcha.textproducer.font.size", "40");
//        验证码文本字符内容范围 默认为abced2345678gfynmnpwx
        properties.setProperty("kaptcha.textproducer.char.string", "abcde0123456789fghijklmnopqrstuvwxyz");
        //字符长度，默认为5
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //字符间距 默认为2
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        //验证码图片宽度 默认为200
        properties.setProperty("kaptcha.image.width", "150");
        //验证码图片高度 默认为40
        properties.setProperty("kaptcha.image.height", "50");
        //背景渐变开始颜色
        properties.setProperty("kaptcha.background.clear.from", "white");
        //背景渐变结束颜色
        properties.setProperty("kaptcha.background.clear.to", "white");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    public R getCaptcha() {
        DefaultKaptcha defaultKaptcha = getDefaultKaptcha();
        //---------------------------生成验证码----------------------
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        // 将验证码放到redis中，生成对应的id和失效时间 {"captchaId": "text"} 过期时间五分钟
        String captchaId = UUID.randomUUID().toString();
//        System.out.println(text + "--" + captchaId);
        redisTemplate.opsForValue().set(captchaId, text, captchaTimeout, TimeUnit.MINUTES);
        //根据文本内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            //输出流输出图片,格式为jpg
            ImageIO.write(image, "jpeg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r|\n", "");
            Map<String, String> result = new HashMap<>();
            result.put("captchaId", captchaId);
            result.put("captchaBase64", captchaBase64);
            return new R(0, "验证码五分钟内有效", result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return R.failure(Code.SYSTEM_INNER_ERROR);
    }

    //根据captchaId和captcha验证图像验证码
    public boolean verifyCaptcha(String captchaId, String captcha) {
        if (isOpenCaptcha) {
            //开启图像验证码则需要验证
            // 获取验证码与redis中存储验证码进行比较
            String redisCaptcha = (String) redisTemplate.opsForValue().get(captchaId);
            if (redisCaptcha == null) {
                // 验证码失效
                BusinessException.error(Code.USER_CAPTCHA_EXPIRE);
                return false;
            }
            if (redisCaptcha.equals(captcha)) {
                // 验证码匹配成功
                redisTemplate.delete(captchaId);
                return true;
            } else {
                // 验证码匹配失败
                BusinessException.error(Code.USER_CAPTCHA_ERROR);
                return false;
            }
        }
        //未开启图像验证码则直接返回true
        return true;
    }

}
