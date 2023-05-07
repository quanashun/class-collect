package top.ashun.recruit.config;


import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ashun.recruit.pojo.vo.Code;


/**
 * @author 18483
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private Code resposeCode;

    public BusinessException(String message) {
        super(message);
    }


    public BusinessException(Code resposeCode) {
        this.resposeCode = resposeCode;
    }

    public static void error(String message) {
        throw new BusinessException(message);
    }


    public static void error(Code resposeCode) {
        throw new BusinessException(resposeCode);
    }

}
