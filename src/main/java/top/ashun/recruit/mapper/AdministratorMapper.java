package top.ashun.recruit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.ashun.recruit.entity.Administrator;

import java.util.List;

/**
 * @author 18483
 */
public interface AdministratorMapper extends BaseMapper<Administrator> {
    List<String> getRolesById(String userId);
}
