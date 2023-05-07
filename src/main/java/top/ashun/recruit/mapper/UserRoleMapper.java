package top.ashun.recruit.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import top.ashun.recruit.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qks
 * @since 2023-04-21
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<String> getRoleListByUserId(@Param("userId") String userId);
}
