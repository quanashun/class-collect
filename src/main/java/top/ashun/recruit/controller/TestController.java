package top.ashun.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 18483
 */
@RestController
public class TestController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    public List<String> anonymityTest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @GetMapping("/putRedis")
    public String test(String s){
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set("myKey", s);
        return  valueOps.get("myKey");
    }

    @GetMapping("/adminTest")
//    @PreAuthorize("hasRole('ROLE_admin')")
    //两种写法都可以
    @PreAuthorize("hasRole('admin')")
    public String adminTest(String s){
        return s;
    }
}
