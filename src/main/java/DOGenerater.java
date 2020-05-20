import com.alibaba.fastjson.JSON;
import lombok.Data;
import util.UnderlineHumpConvertUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 生成实体类
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-05-20 19:31
 */
public class DOGenerater {

    public static void main(String[] args) {
        String[] rows = Generater.sql.split("\n");
        List<String> rowList = Arrays.stream(rows).map(String::trim).collect(Collectors.toList());
        rowList.removeIf(row -> !row.startsWith("`"));
        List<SqlColumn> list = new ArrayList<>();
        for (String row : rowList) {
            String[] strs = row.split(" ");
            SqlColumn sqlColumn = new SqlColumn();
            sqlColumn.name = strs[0].substring(1, strs[0].length() - 1);
            sqlColumn.javaName = UnderlineHumpConvertUtil.underlineToHump(sqlColumn.name);
            sqlColumn.typeDef = strs[1];
            sqlColumn.javaType = sqlTypeToJavaType(sqlColumn.typeDef);
            if (strs[strs.length - 2].equalsIgnoreCase("comment")) {
                sqlColumn.comment = strs[strs.length - 1].substring(1, strs[strs.length - 1].length() - 1);
            } else {
                sqlColumn.comment = sqlColumn.javaName;
            }

            if (!sqlColumn.javaName.equals("id") && !sqlColumn.javaName.equals("createTime") && !sqlColumn.javaName.equals("updateTime")) {
                list.add(sqlColumn);
            }
        }
        System.out.println(JSON.toJSONString(list));

        Class<?> clazz = createDOFile();
    }

    private static Class<?> createDOFile() {

    }

    private static String sqlTypeToJavaType(String typeDef) {
        typeDef = typeDef.toLowerCase();
        if (typeDef.contains("date") || typeDef.contains("time")) {
            return "Date";
        }
        if (typeDef.contains("tinyint")) {
            return "Integer";
        }
        if (typeDef.contains("int")) {
            return "Long";
        }
        return "String";
    }

    @Data
    private static class SqlColumn {

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
}
