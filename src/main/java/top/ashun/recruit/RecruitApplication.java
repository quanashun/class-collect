package top.ashun.recruit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author 18483
 */
@SpringBootApplication
@MapperScan("top.ashun.recruit.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RecruitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class, args);
    }

}
