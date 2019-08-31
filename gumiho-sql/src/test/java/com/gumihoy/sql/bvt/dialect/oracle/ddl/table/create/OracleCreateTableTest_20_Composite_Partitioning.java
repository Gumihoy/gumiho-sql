package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_20_Composite_Partitioning {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE composite_sales\n" +
                "    ( prod_id        NUMBER(6)\n" +
                "    , cust_id        NUMBER\n" +
                "    , time_id        DATE\n" +
                "    , channel_id     CHAR(1)\n" +
                "    , promo_id       NUMBER(6)\n" +
                "    , quantity_sold  NUMBER(3)\n" +
                "    , amount_sold         NUMBER(10,2)\n" +
                "    ) \n" +
                "PARTITION BY RANGE (time_id)\n" +
                "SUBPARTITION BY HASH (channel_id)\n" +
                "  (PARTITION SALES_Q1_1998 VALUES LESS THAN (TO_DATE('01-APR-1998','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q2_1998 VALUES LESS THAN (TO_DATE('01-JUL-1998','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q3_1998 VALUES LESS THAN (TO_DATE('01-OCT-1998','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q4_1998 VALUES LESS THAN (TO_DATE('01-JAN-1999','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q1_1999 VALUES LESS THAN (TO_DATE('01-APR-1999','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q2_1999 VALUES LESS THAN (TO_DATE('01-JUL-1999','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q3_1999 VALUES LESS THAN (TO_DATE('01-OCT-1999','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q4_1999 VALUES LESS THAN (TO_DATE('01-JAN-2000','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q1_2000 VALUES LESS THAN (TO_DATE('01-APR-2000','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q2_2000 VALUES LESS THAN (TO_DATE('01-JUL-2000','DD-MON-YYYY'))\n" +
                "      SUBPARTITIONS 8,\n" +
                "   PARTITION SALES_Q3_2000 VALUES LESS THAN (TO_DATE('01-OCT-2000','DD-MON-YYYY'))\n" +
                "     (SUBPARTITION ch_c,\n" +
                "      SUBPARTITION ch_i,\n" +
                "      SUBPARTITION ch_p,\n" +
                "      SUBPARTITION ch_s,\n" +
                "      SUBPARTITION ch_t),\n" +
                "   PARTITION SALES_Q4_2000 VALUES LESS THAN (MAXVALUE)\n" +
                "      SUBPARTITIONS 4)\n" +
                ";";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE composite_sales (\n" +
                "\tprod_id NUMBER(6),\n" +
                "\tcust_id NUMBER,\n" +
                "\ttime_id DATE,\n" +
                "\tchannel_id CHAR(1),\n" +
                "\tpromo_id NUMBER(6),\n" +
                "\tquantity_sold NUMBER(3),\n" +
                "\tamount_sold NUMBER(10, 2)\n" +
                ")\n" +
                "PARTITION BY RANGE (time_id)\n" +
                "SUBPARTITION BY HASH (channel_id) (\n" +
                "\tPARTITION SALES_Q1_1998 VALUES LESS THAN (TO_DATE('01-APR-1998', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q2_1998 VALUES LESS THAN (TO_DATE('01-JUL-1998', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q3_1998 VALUES LESS THAN (TO_DATE('01-OCT-1998', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q4_1998 VALUES LESS THAN (TO_DATE('01-JAN-1999', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q1_1999 VALUES LESS THAN (TO_DATE('01-APR-1999', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q2_1999 VALUES LESS THAN (TO_DATE('01-JUL-1999', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q3_1999 VALUES LESS THAN (TO_DATE('01-OCT-1999', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q4_1999 VALUES LESS THAN (TO_DATE('01-JAN-2000', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q1_2000 VALUES LESS THAN (TO_DATE('01-APR-2000', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q2_2000 VALUES LESS THAN (TO_DATE('01-JUL-2000', 'DD-MON-YYYY'))\n" +
                "\t\tSUBPARTITIONS 8,\n" +
                "\tPARTITION SALES_Q3_2000 VALUES LESS THAN (TO_DATE('01-OCT-2000', 'DD-MON-YYYY')) (\n" +
                "\t\tSUBPARTITION ch_c,\n" +
                "\t\tSUBPARTITION ch_i,\n" +
                "\t\tSUBPARTITION ch_p,\n" +
                "\t\tSUBPARTITION ch_s,\n" +
                "\t\tSUBPARTITION ch_t\n" +
                "\t),\n" +
                "\tPARTITION SALES_Q4_2000 VALUES LESS THAN (MAXVALUE)\n" +
                "\t\tSUBPARTITIONS 4\n" +
                ");", format);
    }


    @Test
    public void test_1() {
        String sql = "CREATE TABLE customers_part (\n" +
                "   customer_id        NUMBER(6),\n" +
                "   cust_first_name    VARCHAR2(20),\n" +
                "   cust_last_name     VARCHAR2(20),\n" +
                "   nls_territory      VARCHAR2(30),\n" +
                "   credit_limit       NUMBER(9,2)) \n" +
                "   PARTITION BY RANGE (credit_limit)\n" +
                "   SUBPARTITION BY LIST (nls_territory)\n" +
                "      SUBPARTITION TEMPLATE \n" +
                "         (SUBPARTITION east  VALUES \n" +
                "            ('CHINA', 'JAPAN', 'INDIA', 'THAILAND'),\n" +
                "          SUBPARTITION west VALUES \n" +
                "             ('AMERICA', 'GERMANY', 'ITALY', 'SWITZERLAND'),\n" +
                "          SUBPARTITION other VALUES (DEFAULT))\n" +
                "      (PARTITION p1 VALUES LESS THAN (1000),\n" +
                "       PARTITION p2 VALUES LESS THAN (2500),\n" +
                "       PARTITION p3 VALUES LESS THAN (MAXVALUE));";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE customers_part (\n" +
                "\tcustomer_id NUMBER(6),\n" +
                "\tcust_first_name VARCHAR2(20),\n" +
                "\tcust_last_name VARCHAR2(20),\n" +
                "\tnls_territory VARCHAR2(30),\n" +
                "\tcredit_limit NUMBER(9, 2)\n" +
                ")\n" +
                "PARTITION BY RANGE (credit_limit)\n" +
                "SUBPARTITION BY LIST (nls_territory)\n" +
                "\tSUBPARTITION TEMPLATE (\n" +
                "\t\tSUBPARTITION east VALUES ('CHINA', 'JAPAN', 'INDIA', 'THAILAND'),\n" +
                "\t\tSUBPARTITION west VALUES ('AMERICA', 'GERMANY', 'ITALY', 'SWITZERLAND'),\n" +
                "\t\tSUBPARTITION other VALUES (DEFAULT)\n" +
                "\t) (\n" +
                "\tPARTITION p1 VALUES LESS THAN (1000),\n" +
                "\tPARTITION p2 VALUES LESS THAN (2500),\n" +
                "\tPARTITION p3 VALUES LESS THAN (MAXVALUE)\n" +
                ");", format);
    }
}
