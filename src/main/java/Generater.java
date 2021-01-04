import org.apache.commons.lang3.RandomStringUtils;
import xin.allonsy.utils.DateUtils;
import xin.allonsy.utils.FileUtil;
import xin.allonsy.utils.UnderscoreCamelCaseConvertUtil;

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
    public static String entityName = "NewStockHkJisilu";
    public static String sql = "CREATE TABLE `new_stock_hk_jisilu` (\n" +
            "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
            "  `stock_code` varchar(256) DEFAULT NULL COMMENT 'stock代码 stock_cd',\n" +
            "  `stock_name` varchar(256) DEFAULT NULL COMMENT 'stock名称 stock_nm',\n" +
            "  `market` varchar(256) DEFAULT NULL COMMENT '上市板块 market',\n" +
            "  `apply_date` varchar(256) DEFAULT NULL COMMENT '申购起始 apply_dt',\n" +
            "  `apply_date2` datetime DEFAULT NULL COMMENT '申购起始yyyy-MM-dd apply_dt',\n" +
            "  `apply_end_date` varchar(256) DEFAULT NULL COMMENT '申购截止 apply_end_dt',\n" +
            "  `apply_end_date2` datetime DEFAULT NULL COMMENT '申购截止yyyy-MM-dd apply_end_dt2',\n" +
            "  `gray_date` datetime DEFAULT NULL COMMENT '暗盘日 gray_dt',\n" +
            "  `list_date` datetime DEFAULT NULL COMMENT '上市日 list_dt',\n" +
            "  `price_min` decimal(10,4) DEFAULT NULL COMMENT '最低询价（港元） price_range',\n" +
            "  `price_max` decimal(10,4) DEFAULT NULL COMMENT '最高询价（港元） price_range',\n" +
            "  `issue_price` decimal(10,4) DEFAULT NULL COMMENT '发行价 issue_price',\n" +
            "  `issue_pe_min` decimal(10,4) DEFAULT NULL COMMENT '发行价pe最低 issue_pe_range',\n" +
            "  `issue_pe_max` decimal(10,4) DEFAULT NULL COMMENT '发行价pe最高 issue_pe_range',\n" +
            "  `jsl_advise` varchar(32) DEFAULT NULL COMMENT '是否建议申购 jsl_advise',\n" +
            "  `jsl_first_incr_rate` varchar(128) DEFAULT NULL COMMENT '预测首日涨幅？ jsl_first_incr_rt',\n" +
            "  `green_rate` varchar(128) DEFAULT NULL COMMENT '绿鞋保护/公开发售（带百分号的字符串） green_rate',\n" +
            "  `green_amount` decimal(10,4) DEFAULT NULL COMMENT '绿鞋数量（万股） green_amount',\n" +
            "  `ref_company` varchar(128) DEFAULT NULL COMMENT '可比公司 ref_company',\n" +
            "  `above_rate` decimal(10,4) DEFAULT NULL COMMENT '超购倍数 above_rt',\n" +
            "  `jsl_above_rate` decimal(10,4) DEFAULT NULL COMMENT '预测超购倍数 jsl_above_rate',\n" +
            "  `single_draw_money` decimal(10,4) DEFAULT NULL COMMENT '一手资金（港元） single_draw_money',\n" +
            "  `lucky_draw_rate` decimal(10,4) DEFAULT NULL COMMENT '一手中签率（不含%） lucky_draw_rt',\n" +
            "  `raise_money` decimal(10,4) DEFAULT NULL COMMENT '募资金额（亿港元） raise_money',\n" +
            "  `gray_incr_rate` decimal(10,4) DEFAULT NULL COMMENT '利弗莫尔暗盘涨福（不含%） gray_incr_rt',\n" +
            "  `gray_incr_rate2` decimal(10,4) DEFAULT NULL COMMENT 'xx暗盘涨福（不含%） gray_incr_rt2',\n" +
            "  `first_incr_rt` decimal(10,4) DEFAULT NULL COMMENT '首日涨福（不含%） first_incr_rt',\n" +
            "  `total_incr_rt` decimal(10,4) DEFAULT NULL COMMENT '至今涨幅（不含%） total_incr_rt',\n" +
            "  `underwriter` varchar(128) DEFAULT NULL COMMENT '承销商 underwriter',\n" +
            "  `prospectus` varchar(2048) DEFAULT NULL COMMENT '招股说明书 prospectus',\n" +
            "  `iporesult` varchar(2048) DEFAULT NULL COMMENT 'ipo配售结果 iporesult',\n" +
            "  `apply_flg` tinyint(4) unsigned DEFAULT NULL COMMENT 'apply_flg',\n" +
            "  `list_flg` tinyint(4) unsigned DEFAULT NULL COMMENT 'list_flg',\n" +
            "  `status_cd` varchar(32) DEFAULT NULL COMMENT 'status_cd',\n" +
            "  `yx_rate` decimal(10,4) DEFAULT NULL COMMENT 'yx_rate',\n" +
            "  `has_above_rt` varchar(32) DEFAULT NULL COMMENT '是否正常上市true/false has_above_rt',\n" +
            "  `notes` varchar(512) DEFAULT NULL COMMENT 'notes',\n" +
            "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `idx_stock_code` (`stock_code`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='集思录港股新股数据'";

    public static String tableName = UnderscoreCamelCaseConvertUtil.camelCaseToUnderscore(entityName);
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
    public static String corePackage = "com.wangshuai.crawler.dal";
    public static String doPackage = corePackage + ".dataobject";
    public static String mapperPackage = corePackage + ".mapper";
    public static String queryPackage = corePackage + ".query";
    public static String daoPackage = corePackage + ".dao";

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
            tempBuilder.append(tableName).append('.').append(sqlColumn.getName()).append(',');
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
                tempBuilder.append("<if test=\"item." + fieldName + " != null\">\n" +
                        "                    " + dbFieldName + " = #{item." + fieldName + "},\n" +
                        "                </if>");
            } else {
                tempBuilder.append("<if test=\"item." + fieldName + " != null and item." + fieldName + " != '' \">\n" +
                        "                    " + dbFieldName + " = #{item." + fieldName + "},\n" +
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
        fileText = fileText.replaceAll("%%serialVersionUID%%", RandomStringUtils.randomNumeric(18).replaceAll("0", "1"));
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
