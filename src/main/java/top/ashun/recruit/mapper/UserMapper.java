package top.ashun.recruit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.ashun.recruit.entity.User;

import java.util.List;

/**
 * @author 18483
 */
public interface UserMapper extends BaseMapper<User> {
    List<String> getRolesById(String userId);
}
