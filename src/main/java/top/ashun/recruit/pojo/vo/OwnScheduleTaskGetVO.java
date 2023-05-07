package top.ashun.recruit.pojo.vo;

import lombok.Data;

/**
 * @author 18483
 */
@Data
public class OwnScheduleTaskGetVO {
    private String taskId;
    private String title;
    private Integer numberOfFilled;
    private String remark;
    private String address;
}
