package top.ashun.recruit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ashun.recruit.pojo.vo.R;
import top.ashun.recruit.pojo.vo.UserRegisterVO;
import top.ashun.recruit.service.UserServiceImpl;

/**
 * @author 18483
 */
@RestController
@RequestMapping("/registers")
@Api(tags = "用户注册")
public class RegisterController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/userRegister")
    @ApiOperation("普通用户注册")
    public R userRegister(@RequestBody UserRegisterVO vo) {
        return R.success(userService.registerUser(vo.getPhone(), vo.getUsername(), vo.getPassword(),vo.getCaptchaId(), vo.getCaptcha()));
    }


}
