package top.ashun.recruit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("schedule_collect_tasks")
public class ScheduleCollectTasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "task_id", type = IdType.ASSIGN_ID)
    private Long taskId;

    private String taskName;

    private Long userId;

    private String userName;

    private String remark;

    /**
     * 是否发布（1是|0否）
     */
    private Boolean isPublish;

    /**
     * 截止时间
     */
    private LocalDateTime deadline;


}
