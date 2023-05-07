package top.ashun.recruit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.ashun.recruit.service.AdministratorServiceImpl;
import top.ashun.recruit.service.UserRoleServiceImpl;
import top.ashun.recruit.service.UserServiceImpl;

@SpringBootTest
class RecruitApplicationTests {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AdministratorServiceImpl administratorService;
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    void contextLoads() {
        System.out.println(userService.getBaseMapper().selectById("0129986820"));
        System.out.println(administratorService.getBaseMapper().selectById("0390590296"));
        System.out.println(userRoleService.getBaseMapper().getRoleListByUserId("0390590296"));
    }

    @Test
    void passwordEncoderTest() {
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }


}
