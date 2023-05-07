package top.ashun.recruit.pojo.vo;

import lombok.Data;

/**
 * @author 18483
 */
@Data
public class GetOneCellFulledNamesVO {

    private Long taskId;

    private Integer tableId;
    /**
     * 周一|周二|周三|等
     */
    private String date;

    //第几节课
    private String number;

}
