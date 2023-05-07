package top.ashun.recruit.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import top.ashun.recruit.entity.ScheduleCollectResults;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qks
 * @since 2023-05-06
 */
public interface ScheduleCollectResultsMapper extends BaseMapper<ScheduleCollectResults> {

    Integer computeNumberOfFilled(@Param("taskId") Long taskId);
}
