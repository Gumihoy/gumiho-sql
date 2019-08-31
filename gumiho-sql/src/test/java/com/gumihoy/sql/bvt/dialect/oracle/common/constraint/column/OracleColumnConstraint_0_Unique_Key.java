package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.column;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleColumnConstraint_0_Unique_Key {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE promotions_var1\n" +
                "    ( promo_id         NUMBER(6)\n" +
                "                       CONSTRAINT promo_id_u  UNIQUE\n" +
                "    , promo_name       VARCHAR2(20)\n" +
                "    , promo_category   VARCHAR2(15)\n" +
                "    , promo_cost       NUMBER(10,2)\n" +
                "    , promo_begin_date DATE\n" +
                "    , promo_end_date   DATE\n" +
                "    ) ;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE promotions_var1 (\n" +
                "\tpromo_id NUMBER(6) CONSTRAINT promo_id_u UNIQUE,\n" +
                "\tpromo_name VARCHAR2(20),\n" +
                "\tpromo_category VARCHAR2(15),\n" +
                "\tpromo_cost NUMBER(10, 2),\n" +
                "\tpromo_begin_date DATE,\n" +
                "\tpromo_end_date DATE\n" +
                ");", format);
    }

}
