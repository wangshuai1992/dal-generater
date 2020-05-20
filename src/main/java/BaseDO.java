import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by guotie on 2018/11/21.
 */
@Data
public class BaseDO implements Serializable {

    private static final long serialVersionUID = -5924053309868130021L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
