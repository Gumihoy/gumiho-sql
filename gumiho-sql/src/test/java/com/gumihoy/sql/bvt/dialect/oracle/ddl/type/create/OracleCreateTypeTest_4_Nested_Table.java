package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTypeTest_4_Nested_Table {

    @Test
    public void test_0() {
        String sql = "CREATE TYPE cust_address_typ2 AS OBJECT\n" +
                "       ( street_address     VARCHAR2(40)\n" +
                "       , postal_code        VARCHAR2(10)\n" +
                "       , city               VARCHAR2(30)\n" +
                "       , state_province     VARCHAR2(10)\n" +
                "       , country_id         CHAR(2)\n" +
                "       , phone              phone_list_typ_demo\n" +
                "       );\n" +
                "\n" +
                "CREATE TYPE cust_nt_address_typ\n" +
                "   AS TABLE OF cust_address_typ2;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TYPE cust_address_typ2 AS OBJECT\n" +
                "       ( street_address     VARCHAR2(40)\n" +
                "       , postal_code        VARCHAR2(10)\n" +
                "       , city               VARCHAR2(30)\n" +
                "       , state_province     VARCHAR2(10)\n" +
                "       , country_id         CHAR(2)\n" +
                "       , phone              phone_list_typ_demo\n" +
                "       );\n" +
                "\n" +
                "CREATE TYPE cust_nt_address_typ\n" +
                "   AS TABLE OF cust_address_typ2;", format);
    }

}
