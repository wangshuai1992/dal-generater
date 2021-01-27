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
    public static String entityName = "AmoebaItemSku";
    public static String sql = "CREATE TABLE `amoeba_item_sku` (\n" +
            "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
            "  `item_id` bigint(20) unsigned NOT NULL COMMENT '商品id',\n" +
            "  `sku_id` bigint(20) unsigned NOT NULL COMMENT 'skuId',\n" +
            "  `shop_id` bigint(20) NOT NULL COMMENT '第三方店铺id',\n" +
            "  `third_product_id` varchar(32) NOT NULL COMMENT '第三方商品id',\n" +
            "  `third_sku_id` varchar(32) NOT NULL COMMENT '第三方skuId',\n" +
            "  `third_sku_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '第三方sku状态 0-下架，1-在架',\n" +
            "  `third_sku_code` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方sku编码',\n" +
            "  `third_sku_option` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方sku尺码等信息',\n" +
            "  `inventory_item_id` varchar(32) DEFAULT NULL COMMENT '第三方库存id',\n" +
            "  `cost` decimal(20,2) DEFAULT NULL COMMENT 'sku的成本价（美元）',\n" +
            "  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',\n" +
            "  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',\n" +
            "  `is_deleted` tinyint(4) NOT NULL COMMENT '是否已删除（0未删除，1已删除）',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  KEY `idx_item_id_shop_id` (`item_id`,`shop_id`),\n" +
            "  KEY `idx_third_product_id` (`third_product_id`),\n" +
            "  KEY `idx_sku_id` (`sku_id`),\n" +
            "  KEY `idx_create_time` (`create_time`),\n" +
            "  KEY `idx_update_time` (`update_time`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品sku绑定关系表'\n";

    //逻辑删除字段
    public static String logicDeleteColumn = "is_deleted";
    public static boolean hasLogicDeleteColumn = sql.contains("`" + logicDeleteColumn + "`");

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
    public static String corePackage = "com.clubfactory.amoeba.product.manager.core";
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
        // 逻辑删除字段单独处理
        sqlColumns.removeIf(sqlColumn -> logicDeleteColumn.equals(sqlColumn.getName()));
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
        String javaFile = hasLogicDeleteColumn ? FileUtil.readFile(resourcePath + "/template/TemplateQueryWithLogicDelete.java") : FileUtil.readFile(resourcePath + "/template/TemplateQuery.java");
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
        String xml = hasLogicDeleteColumn ? FileUtil.readFile(resourcePath + "/template/TemplateMapperWithLogicDelete.xml") : FileUtil.readFile(resourcePath + "/template/TemplateMapper.xml");
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
            if (hasLogicDeleteColumn && sqlColumn.getName().equals(logicDeleteColumn)) {
                continue;
            }
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
            if (hasLogicDeleteColumn && sqlColumn.getName().equals(logicDeleteColumn)) {
                continue;
            }
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
            if (hasLogicDeleteColumn && sqlColumn.getName().equals(logicDeleteColumn)) {
                continue;
            }
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
            if (hasLogicDeleteColumn && sqlColumn.getName().equals(logicDeleteColumn)) {
                continue;
            }
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
