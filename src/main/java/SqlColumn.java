import lombok.Data;

/**
 * SqlColumn
 *
 * @author wangshuai
 * @date 2020-05-20 21:06
 */
@Data
public class SqlColumn {

    /**
     * 列名
     */
    private String name;

    /**
     * 实体类字段名
     */
    private String javaName;

    /**
     * 列类型定义  如int(11)、varchar(128)
     */
    private String typeDef;

    /**
     * java字段类型 String、Date
     */
    private String javaType;

    /**
     * 备注
     */
    private String comment;

}
