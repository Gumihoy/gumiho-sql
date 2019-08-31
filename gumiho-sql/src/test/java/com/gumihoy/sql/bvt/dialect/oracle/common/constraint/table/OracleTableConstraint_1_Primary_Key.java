package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.table;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleTableConstraint_1_Primary_Key {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE locations_demo\n" +
                "    ( location_id    NUMBER(4) \n" +
                "    , street_address VARCHAR2(40)\n" +
                "    , postal_code    VARCHAR2(12)\n" +
                "    , city           VARCHAR2(30)\n" +
                "    , state_province VARCHAR2(25)\n" +
                "    , country_id     CHAR(2)\n" +
                "    , CONSTRAINT loc_id_pk PRIMARY KEY (location_id));";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE locations_demo\n" +
                "    ( location_id    NUMBER(4) \n" +
                "    , street_address VARCHAR2(40)\n" +
                "    , postal_code    VARCHAR2(12)\n" +
                "    , city           VARCHAR2(30)\n" +
                "    , state_province VARCHAR2(25)\n" +
                "    , country_id     CHAR(2)\n" +
                "    , CONSTRAINT loc_id_pk PRIMARY KEY (location_id));", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE sales \n" +
                "    ADD CONSTRAINT sales_pk PRIMARY KEY (prod_id, cust_id) DISABLE; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales \n" +
                "    ADD CONSTRAINT sales_pk PRIMARY KEY (prod_id, cust_id) DISABLE; ", format);
    }
}
