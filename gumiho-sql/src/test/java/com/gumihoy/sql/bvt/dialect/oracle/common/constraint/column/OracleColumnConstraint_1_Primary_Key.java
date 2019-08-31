package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.column;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleColumnConstraint_1_Primary_Key {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE locations_demo\n" +
                "    ( location_id    NUMBER(4) CONSTRAINT loc_id_pk PRIMARY KEY\n" +
                "    , street_address VARCHAR2(40)\n" +
                "    , postal_code    VARCHAR2(12)\n" +
                "    , city           VARCHAR2(30)\n" +
                "    , state_province VARCHAR2(25)\n" +
                "    , country_id     CHAR(2)\n" +
                "    ) ;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE locations_demo (\n" +
                "\tlocation_id NUMBER(4) CONSTRAINT loc_id_pk PRIMARY KEY,\n" +
                "\tstreet_address VARCHAR2(40),\n" +
                "\tpostal_code VARCHAR2(12),\n" +
                "\tcity VARCHAR2(30),\n" +
                "\tstate_province VARCHAR2(25),\n" +
                "\tcountry_id CHAR(2)\n" +
                ");", format);
    }

}
