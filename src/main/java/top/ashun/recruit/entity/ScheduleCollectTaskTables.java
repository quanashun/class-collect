package top.ashun.recruit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author qks
 * @since 2023-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("schedule_collect_task_tables")
public class ScheduleCollectTaskTables implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long taskId;

    /**
     * 某一任务下第几张课表
     */
    private Integer tableId;

    /**
     * 表的名字，如（第十四周课表）
     */
    private String tableName;


}
