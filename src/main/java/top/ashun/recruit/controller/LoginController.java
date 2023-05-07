package top.ashun.recruit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ashun.recruit.pojo.vo.R;
import top.ashun.recruit.pojo.vo.UserPasswordLoginVO;
import top.ashun.recruit.service.AdministratorServiceImpl;
import top.ashun.recruit.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author 18483
 */
@RestController
@RequestMapping("/logins")
@Api(tags = "用户登录")
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdministratorServiceImpl administratorService;

    //普通用户密码登录
    @PostMapping("/userPasswordLogin")
    @ApiOperation("普通用户手机号密码登录")
    public R userLoginByPassword(@RequestBody @Valid UserPasswordLoginVO userPasswordLoginVO) {
        return R.success(userService.loginByPhone(userPasswordLoginVO.getUsername(), userPasswordLoginVO.getPassword()));
    }

    //普通用户验证码登录


    //管理员密码登录
    @PostMapping("/adminPasswordLogin")
    @ApiOperation("管理员密码登录")
    public R adminLoginByPassword(@RequestBody @Valid UserPasswordLoginVO userPasswordLoginVO, HttpServletRequest request) {
        return R.success(administratorService.getUserTokenByPassword(userPasswordLoginVO.getUsername(), userPasswordLoginVO.getPassword()));
    }

    //管理员验证码登录

}
