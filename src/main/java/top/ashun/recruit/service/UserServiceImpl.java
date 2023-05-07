package top.ashun.recruit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.ashun.recruit.config.BusinessException;
import top.ashun.recruit.entity.User;
import top.ashun.recruit.entity.UserInfo;
import top.ashun.recruit.mapper.UserMapper;
import top.ashun.recruit.pojo.vo.Code;
import top.ashun.recruit.pojo.vo.UserLoginRespVO;
import top.ashun.recruit.util.captcha.ImageCaptchaUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 18483
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AdministratorServiceImpl administratorService;
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private ImageCaptchaUtil imageCaptchaUtil;

    public UserLoginRespVO loginByPhone(String phone, String rawPassword) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone", phone);
        User user = baseMapper.selectOne(userQueryWrapper);
        if (user == null) {
            BusinessException.error(Code.USER_LOGIN_ERROR);
        }
        return getUserTokenByPassword(user.getId(), rawPassword);
    }

    public UserLoginRespVO getUserTokenByPassword(String userId, String rawPassword) {
        User user = baseMapper.selectById(userId);
        if (user != null) {
            //密码匹配成功
            if (bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(user, userInfo);
                List<String> roles = userRoleService.getBaseMapper().getRoleListByUserId(userId);
                //普通用户的通用角色（common_user，具体根据业务来）
                if (!roles.contains("COMMON_USER")) {
                    roles.add("COMMON_USER");
                }
                return administratorService.generateResponseVO(userInfo, roles);
            }
        }
        BusinessException.error(Code.USER_LOGIN_ERROR);
        return null;
    }

    public String registerUser(String phone, String username, String password, String captchaId, String captcha) {
        if (imageCaptchaUtil.verifyCaptcha(captchaId, captcha)) {
            if (isPhoneNotInUse(phone)) {
                User user = new User();
                user.setUsername(username);
                user.setPhone(phone);
                user.setPassword(bCryptPasswordEncoder.encode(password));
                baseMapper.insert(user);
                return "注册成功！";
            }
            BusinessException.error(Code.USER_HAS_EXISTED);
            return null;
        }
        return null;
    }

    public boolean isPhoneNotInUse(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        List<User> users = baseMapper.selectByMap(map);
        return users.isEmpty();
    }
}
