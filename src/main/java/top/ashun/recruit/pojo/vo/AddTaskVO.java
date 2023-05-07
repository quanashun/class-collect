package top.ashun.recruit.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 18483
 */
@Data
public class AddTaskVO {
    private String taskName;
    private String remark;
    private List<String> tableNames;
}
