package top.ashun.recruit.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.ashun.recruit.config.BusinessException;
import top.ashun.recruit.entity.Administrator;
import top.ashun.recruit.entity.UserInfo;
import top.ashun.recruit.mapper.AdministratorMapper;
import top.ashun.recruit.pojo.dto.UserTokenDTO;
import top.ashun.recruit.pojo.vo.Code;
import top.ashun.recruit.pojo.vo.UserLoginRespVO;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 18483
 */
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserRoleServiceImpl userRoleService;

    @Value("${tokenTimeOut: 120}")
    private int tokenTimeOut;

    public UserLoginRespVO getUserTokenByPassword(String userId, String rawPassword) {
        Administrator user = baseMapper.selectById(userId);
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
                //管理员的通用角色（admin，具体根据业务来）
                if (!roles.contains("ADMIN")) {
                    roles.add("ADMIN");
                }
                return generateResponseVO(userInfo, roles);
            }
        }
        BusinessException.error(Code.USER_LOGIN_ERROR);
        return null;
    }

    //生成token和对应的userTokenDTO并以token为键、userTokenDTO为值放入redis  最后生成包含userinfo、roles、token的UserLoginRespVO
    public UserLoginRespVO generateResponseVO(UserInfo userInfo, List<String> roles) {
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        UserLoginRespVO resp = new UserLoginRespVO();

        resp.setUserInfo(userInfo);
        userTokenDTO.setUserInfo(userInfo);

        resp.setRoleList(roles);
        userTokenDTO.setRoleList(roles);

        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, userTokenDTO, tokenTimeOut, TimeUnit.HOURS);
        resp.setToken(token);
        return resp;
    }
}
