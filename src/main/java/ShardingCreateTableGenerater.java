/**
 * 分表建表语句生成
 *
 * @author wangshuai
 * @version V1.0
 * @date 2021-11-23 15:04
 */
@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
public class ShardingCreateTableGenerater {

    public static String tableName = "item_sku_price";
    public static int tableNum = 128;
    public static String sql = "CREATE TABLE `item_sku_price` (\n" +
            "    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "    `item_id` bigint(20) unsigned NOT NULL COMMENT 'item id',\n" +
            "    `item_no` varchar(64) NOT NULL COMMENT 'item_no',\n" +
            "    `item_sku_id` bigint(20) unsigned NOT NULL COMMENT 'item sku id',\n" +
            "    `country` varchar(32) NOT NULL COMMENT 'country',\n" +
            "    `channel` varchar(32) NOT NULL COMMENT 'channel',\n" +
            "    `currency` varchar(16) NOT NULL COMMENT '币种',\n" +
            "    `price` decimal(10,4) NOT NULL COMMENT '价格',\n" +
            "    `prime_price` decimal(10,4) DEFAULT NULL COMMENT '会员价',\n" +
            "    `marking_price` decimal(10,4) NOT NULL COMMENT '划线价',\n" +
            "    `po_purchase_price` decimal(10,4) DEFAULT NULL COMMENT 'po采购价',\n" +
            "    `price_type` int(10) NOT NULL COMMENT '价格类型',\n" +
            "    `price_update_time` datetime DEFAULT NULL COMMENT '更新时间',\n" +
            "    `weight` double NOT NULL COMMENT '重量',\n" +
            "    `ext` json DEFAULT NULL COMMENT 'json扩展字段',\n" +
            "    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间UTC',\n" +
            "    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "    PRIMARY KEY (`id`),\n" +
            "    UNIQUE KEY `idx_itemSkuId_country` (`item_sku_id`,`country`),\n" +
            "    KEY `idx_item_id_country` (`item_id`,`country`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品价格表';";

    public static String generateShardingCreateTableDDL() {
        StringBuilder builder = new StringBuilder();
        builder.append(sql.replace(tableName, tableName + "_0")).append("\n\n");
        for (int i = 1; i < tableNum; i++) {
            builder.append("CREATE TABLE " + tableName + "_" + i + " LIKE " + tableName + "_0;\n");
        }
        return builder.toString();
    }

    public static String generateDeleteTable(String name, int num) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            builder.append("DROP TABLE IF EXISTS " + name + "_" + i + ";\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateShardingCreateTableDDL());
//        System.out.println(generateDeleteTable("sku", 128));
    }

}
