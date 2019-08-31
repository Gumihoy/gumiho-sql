package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_18_Partitioning_By_Hash {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE hash_products \n" +
                "    ( product_id          NUMBER(6)   PRIMARY KEY\n" +
                "    , product_name        VARCHAR2(50) \n" +
                "    , product_description VARCHAR2(2000) \n" +
                "    , category_id         NUMBER(2) \n" +
                "    , weight_class        NUMBER(1) \n" +
                "    , warranty_period     INTERVAL YEAR TO MONTH \n" +
                "    , supplier_id         NUMBER(6) \n" +
                "    , product_status      VARCHAR2(20) \n" +
                "    , list_price          NUMBER(8,2) \n" +
                "    , min_price           NUMBER(8,2) \n" +
                "    , catalog_url         VARCHAR2(50) \n" +
                "    , CONSTRAINT          product_status_lov_demo \n" +
                "                          CHECK (product_status in ('orderable' \n" +
                "                                                  ,'planned' \n" +
                "                                                  ,'under development' \n" +
                "                                                  ,'obsolete') \n" +
                " ) ) \n" +
                " PARTITION BY HASH (product_id) \n" +
                " PARTITIONS 4 \n" +
                " STORE IN (tbs_01, tbs_02, tbs_03, tbs_04); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE hash_products (\n" +
                "\tproduct_id NUMBER(6) PRIMARY KEY,\n" +
                "\tproduct_name VARCHAR2(50),\n" +
                "\tproduct_description VARCHAR2(2000),\n" +
                "\tcategory_id NUMBER(2),\n" +
                "\tweight_class NUMBER(1),\n" +
                "\twarranty_period INTERVAL YEAR TO MONTH,\n" +
                "\tsupplier_id NUMBER(6),\n" +
                "\tproduct_status VARCHAR2(20),\n" +
                "\tlist_price NUMBER(8, 2),\n" +
                "\tmin_price NUMBER(8, 2),\n" +
                "\tcatalog_url VARCHAR2(50),\n" +
                "\tCONSTRAINT product_status_lov_demo CHECK (product_status IN ('orderable', 'planned', 'under development', 'obsolete'))\n" +
                ")\n" +
                "PARTITION BY HASH (product_id) PARTITIONS 4\n" +
                "STORE IN (\n" +
                "\ttbs_01,\n" +
                "\ttbs_02,\n" +
                "\ttbs_03,\n" +
                "\ttbs_04\n" +
                ");", format);
    }


}
