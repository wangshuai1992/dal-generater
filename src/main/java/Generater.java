import org.apache.commons.lang3.RandomStringUtils;
import util.DateUtils;
import util.FileUtil;
import util.UnderlineHumpConvertUtil;

import java.util.Date;
import java.util.List;

/**
 * 生成dal层
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-05-20 15:41
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "StringConcatenationInsideStringBufferAppend"})
public class Generater {

    /**
     * edit
     */
    public static String entityName = "SellerInfo";
    public static String sql = "DROP TABLE IF EXISTS `seller_bank_info`;\n" +
            "CREATE TABLE `seller_bank_info` (\n" +
            "    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
            "    `seller_id` INT(11) NOT NULL COMMENT '卖家id',\n" +
            "    `bank_name` varchar(128) NOT NULL,\n" +
            "    `bank_account` varchar(64) NOT NULL,\n" +
            "    `account_holder` varchar(64) NOT NULL,\n" +
            "    `ifsc_code` varchar(16) DEFAULT NULL COMMENT '银行代码',\n" +
            "    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "    PRIMARY KEY (`id`),\n" +
            "    KEY `idx_seller_id` (`seller_id`)\n" +
            ") ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='卖家银行卡信息表';";

    public static String tableName = UnderlineHumpConvertUtil.humpToUnderline(entityName);
    public static String doClassFileName = entityName + "DO.java";
    public static String doClassName = entityName + "DO";
    public static String mapperFileName = entityName + "Mapper.xml";
    public static String mapperClassFileName = entityName + "Mapper.java";
    public static String mapperClassName = entityName + "Mapper";
    public static String queryClassFileName = entityName + "Query.java";
    public static String queryClassName = entityName + "Query";
    public static String daoClassFileName = entityName + "DAO.java";
    public static String daoClassName = entityName + "DAO";
    public static String resourcePath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("/target/classes", "/src/main/resources");

    /**
     * edit
     */
    public static String corePackage = "com.clubfactory.center.seller.core";
    public static String doPackage = corePackage + ".dataobject.seller";
    public static String mapperPackage = corePackage + ".mapper.seller";
    public static String queryPackage = corePackage + ".query.seller";
    public static String daoPackage = corePackage + ".dao.seller";

    public static String doReference = doPackage + "." + doClassName;
    public static String mapperReference = mapperPackage + "." + mapperClassName;
    public static String queryReference = queryPackage + "." + queryClassName;
    public static String daoReference = daoPackage + "." + daoClassName;

    public static void main(String[] args) throws Exception {
        List<SqlColumn> sqlColumns = new DOGenerater().generate();
        createMapperXML(sqlColumns);
        createQueryJava(sqlColumns);
        createMapperJava();
        createDAOJava();
    }

    private static void createDAOJava() throws Exception {
        String javaFile = FileUtil.readFile(resourcePath + "/template/TemplateDAO.java");
        javaFile = globalReplace(javaFile);

        // 生成结果query文件
        FileUtil.createOrOverwriteFile(resourcePath + daoClassFileName, javaFile);
    }

    private static void createMapperJava() throws Exception {
        String javaFile = FileUtil.readFile(resourcePath + "/template/TemplateMapper.java");
        javaFile = globalReplace(javaFile);

        // 生成结果query文件
        FileUtil.createOrOverwriteFile(resourcePath + mapperClassFileName, javaFile);
    }

    private static void createQueryJava(List<SqlColumn> sqlColumns) throws Exception {
        String javaFile = FileUtil.readFile(resourcePath + "/template/TemplateQuery.java");
        javaFile = globalReplace(javaFile);

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

        // 生成结果query文件
        FileUtil.createOrOverwriteFile(resourcePath + queryClassFileName, javaFile);
    }

    private static void createMapperXML(List<SqlColumn> sqlColumns) throws Exception {
        String xml = FileUtil.readFile(resourcePath + "/template/TemplateMapper.xml");
        xml = globalReplace(xml);

        // 替换%%resultMapRow%%
        StringBuilder tempBuilder = new StringBuilder();
        for (int i = 0; i < sqlColumns.size(); i++) {
            SqlColumn sqlColumn = sqlColumns.get(i);
            String fieldName = sqlColumn.getJavaName();
            if (i != 0) {
                tempBuilder.append("        ");
            }
            tempBuilder.append("<result property=\"").append(fieldName).append("\" column=\"").append(sqlColumn.getName()).append("\"/>");
            if (i != sqlColumns.size() - 1) {
                tempBuilder.append("\n");
            }
        }
        xml = xml.replaceAll("%%resultMapRow%%", tempBuilder.toString());

        // 替换%%columnList%%
        tempBuilder = new StringBuilder();
        for (int i = 0; i < sqlColumns.size(); i++) {
            SqlColumn sqlColumn = sqlColumns.get(i);
            if (i != 0) {
                tempBuilder.append("        ");
            }
            tempBuilder.append(sqlColumn.getName()).append(',');
            if (i != sqlColumns.size() - 1) {
                tempBuilder.append("\n");
            }
        }
        xml = xml.replaceAll("%%columnList%%", tempBuilder.toString());

        // 替换%%insertValues%%
        tempBuilder = new StringBuilder();
        for (int i = 0; i < sqlColumns.size(); i++) {
            SqlColumn sqlColumn = sqlColumns.get(i);
            String fieldName = sqlColumn.getJavaName();
            if (i != 0) {
                tempBuilder.append("        ");
            }
            tempBuilder.append("#{").append(fieldName).append("},");
            if (i != sqlColumns.size() - 1) {
                tempBuilder.append("\n");
            }
        }
        xml = xml.replaceAll("%%insertValues%%", tempBuilder.toString());

        // 替换%%batchInsertValues%%
        tempBuilder = new StringBuilder();
        for (int i = 0; i < sqlColumns.size(); i++) {
            SqlColumn sqlColumn = sqlColumns.get(i);
            String fieldName = sqlColumn.getJavaName();
            if (i != 0) {
                tempBuilder.append("            ");
            }
            tempBuilder.append("#{item.").append(fieldName).append("},");
            if (i != sqlColumns.size() - 1) {
                tempBuilder.append("\n");
            }
        }
        xml = xml.replaceAll("%%batchInsertValues%%", tempBuilder.toString());

        // 替换%%updateSet%%
        tempBuilder = new StringBuilder();
        for (int i = 0; i < sqlColumns.size(); i++) {
            SqlColumn sqlColumn = sqlColumns.get(i);
            boolean isString = sqlColumn.getJavaType().equals("String");
            String fieldName = sqlColumn.getJavaName();
            String dbFieldName = sqlColumn.getName();
            if (i != 0) {
                tempBuilder.append("            ");
            }
            if (!isString) {
                tempBuilder.append("<if test=\"" + fieldName + " != null\">\n" +
                        "                " + dbFieldName + " = #{" + fieldName + "},\n" +
                        "            </if>");
            } else {
                tempBuilder.append("<if test=\"" + fieldName + " != null and " + fieldName + " != '' \">\n" +
                        "                " + dbFieldName + " = #{" + fieldName + "},\n" +
                        "            </if>");
            }

            if (i != sqlColumns.size() - 1) {
                tempBuilder.append("\n");
            }
        }
        xml = xml.replaceAll("%%updateSet%%", tempBuilder.toString());

        // 替换%%batchUpdateSet%%
        tempBuilder = new StringBuilder();
        for (int i = 0; i < sqlColumns.size(); i++) {
            SqlColumn sqlColumn = sqlColumns.get(i);
            boolean isString = sqlColumn.getJavaType().equals("String");
            String fieldName = sqlColumn.getJavaName();
            String dbFieldName = sqlColumn.getName();
            if (i != 0) {
                tempBuilder.append("                ");
            }
            if (!isString) {
                tempBuilder.append("<if test=\"" + fieldName + " != null\">\n" +
                        "                    " + dbFieldName + " = #{" + fieldName + "},\n" +
                        "                </if>");
            } else {
                tempBuilder.append("<if test=\"" + fieldName + " != null and " + fieldName + " != '' \">\n" +
                        "                    " + dbFieldName + " = #{" + fieldName + "},\n" +
                        "                </if>");
            }

            if (i != sqlColumns.size() - 1) {
                tempBuilder.append("\n");
            }
        }
        xml = xml.replaceAll("%%batchUpdateSet%%", tempBuilder.toString());

        // 替换%%queryCondition%%
        tempBuilder = new StringBuilder();
        for (int i = 0; i < sqlColumns.size(); i++) {
            SqlColumn sqlColumn = sqlColumns.get(i);
            boolean isString = sqlColumn.getJavaType().equals("String");
            String fieldName = sqlColumn.getJavaName();
            String dbFieldName = sqlColumn.getName();
            if (i != 0) {
                tempBuilder.append("            ");
            }
            if (!isString) {
                tempBuilder.append("<if test=\"" + fieldName + " != null\">\n" +
                        "                AND " + tableName + "." + dbFieldName + " = #{" + fieldName + "}\n" +
                        "            </if>");
            } else {
                tempBuilder.append("<if test=\"" + fieldName + " != null and " + fieldName + " != '' \">\n" +
                        "                AND " + tableName + "." + dbFieldName + " = #{" + fieldName + "}\n" +
                        "            </if>");
            }

            if (i != sqlColumns.size() - 1) {
                tempBuilder.append("\n");
            }
        }
        xml = xml.replaceAll("%%queryCondition%%", tempBuilder.toString());

        // 生成结果mapper文件
        FileUtil.createOrOverwriteFile(resourcePath + mapperFileName, xml);
    }

    public static String globalReplace(String fileText) {
        fileText = fileText.replaceAll("%%date%%", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
        fileText = fileText.replaceAll("%%serialVersionUID%%", RandomStringUtils.randomNumeric(18));
        fileText = fileText.replaceAll("%%tableName%%", tableName);

        fileText = fileText.replaceAll("%%doClassName%%", doClassName);
        fileText = fileText.replaceAll("%%queryClassName%%", queryClassName);
        fileText = fileText.replaceAll("%%mapperClassName%%", mapperClassName);
        fileText = fileText.replaceAll("%%daoClassName%%", daoClassName);

        fileText = fileText.replaceAll("%%doReference%%", doReference);
        fileText = fileText.replaceAll("%%queryReference%%", queryReference);
        fileText = fileText.replaceAll("%%mapperReference%%", mapperReference);
        fileText = fileText.replaceAll("%%daoReference%%", daoReference);

        fileText = fileText.replaceAll("%%doPackage%%", doPackage);
        fileText = fileText.replaceAll("%%queryPackage%%", queryPackage);
        fileText = fileText.replaceAll("%%mapperPackage%%", mapperPackage);
        fileText = fileText.replaceAll("%%daoPackage%%", daoPackage);
        return fileText;
    }

}
