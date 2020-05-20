import com.alibaba.fastjson.JSON;
import util.FileUtil;
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
@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
public class DOGenerater {

    public List<SqlColumn> generate() throws Exception {
        String[] rows = Generater.sql.split("\n");
        List<String> rowList = Arrays.stream(rows).map(String::trim).collect(Collectors.toList());
        rowList.removeIf(row -> !row.startsWith("`"));
        List<SqlColumn> sqlColumns = new ArrayList<>();
        for (String row : rowList) {
            String[] strs = row.split(" ");
            SqlColumn sqlColumn = new SqlColumn();
            sqlColumn.setName(strs[0].substring(1, strs[0].length() - 1));
            sqlColumn.setJavaName(UnderlineHumpConvertUtil.underlineToHump(sqlColumn.getName()));
            sqlColumn.setTypeDef(strs[1]);
            sqlColumn.setJavaType(sqlTypeToJavaType(sqlColumn.getTypeDef()));
            if (strs[strs.length - 2].equalsIgnoreCase("comment")) {
                sqlColumn.setComment(strs[strs.length - 1].substring(1, strs[strs.length - 1].length() - 2));
            } else {
                sqlColumn.setComment(sqlColumn.getJavaName());
            }

            if (!sqlColumn.getJavaName().equals("id") && !sqlColumn.getJavaName().equals("createTime") && !sqlColumn.getJavaName().equals("updateTime")) {
                sqlColumns.add(sqlColumn);
            }
        }
        System.out.println(JSON.toJSONString(sqlColumns));
        createDOFile(sqlColumns);
        return sqlColumns;
    }

    public static void main(String[] args) throws Exception {
        new DOGenerater().generate();
    }

    private void createDOFile(List<SqlColumn> sqlColumns) throws Exception {
        String javaFile = FileUtil.readFile(Generater.resourcePath + "/template/TemplateDO.java");
        javaFile = Generater.globalReplace(javaFile);

        // 替换%%fields%%
        StringBuilder tempBuilder = new StringBuilder();
        for (int i = 0; i < sqlColumns.size(); i++) {
            SqlColumn sqlColumn = sqlColumns.get(i);
            tempBuilder.append("    /**\n" +
                    "     * " + sqlColumn.getComment() + "\n" +
                    "     */\n" +
                    "    private " + sqlColumn.getJavaType() + " " + sqlColumn.getJavaName() + ";");
            if (i != sqlColumns.size() - 1) {
                tempBuilder.append("\n\n");
            }
        }
        javaFile = javaFile.replaceAll("%%fields%%", tempBuilder.toString());

        // 生成结果文件
        FileUtil.createOrOverwriteFile(Generater.resourcePath + Generater.doClassFileName, javaFile);
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

}
