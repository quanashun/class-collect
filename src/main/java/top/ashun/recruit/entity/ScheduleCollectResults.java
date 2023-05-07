package top.ashun.recruit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("schedule_collect_results")
public class ScheduleCollectResults implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long taskId;

    private Integer tableId;

    /**
     * 周一|周二|周三|等
     */
    private String date;

    /**
     * 填写人ID
     */
    private Long userId;

    /**
     * 填写人名字
     */
    private String userName;

    /**
     * 一天中第1节课情况
     */
    private String one;

    /**
     * 一天中第2节课情况
     */
    private String two;

    /**
     * 一天中第3节课情况
     */
    private String three;

    /**
     * 一天中第4节课情况
     */
    private String four;

    /**
     * 一天中第5节课情况
     */
    private String five;

    public static ScheduleCollectResults zeroValueInstance(Long taskId,Integer tableId,String date,Long userId) {
        ScheduleCollectResults zeroValueInstance = new ScheduleCollectResults();
        zeroValueInstance.setTaskId(taskId);
        zeroValueInstance.setTableId(tableId);
        zeroValueInstance.setDate(date);
        zeroValueInstance.setUserId(userId);
        zeroValueInstance.setOne("0");
        zeroValueInstance.setTwo("0");
        zeroValueInstance.setThree("0");
        zeroValueInstance.setFour("0");
        zeroValueInstance.setFive("0");
        return zeroValueInstance;
    }
}
