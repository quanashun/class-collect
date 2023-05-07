package top.ashun.recruit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 18483
 */
@Data
public class User {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String phone;

    private String username;

    private String password;

}
