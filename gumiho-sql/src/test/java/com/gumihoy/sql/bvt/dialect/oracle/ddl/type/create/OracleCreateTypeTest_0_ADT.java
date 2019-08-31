package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTypeTest_0_ADT {

    @Test
    public void test_0() {
        String sql = "CREATE TYPE customer_typ_demo AS OBJECT\n" +
                "    ( customer_id        NUMBER(6)\n" +
                "    , cust_first_name    VARCHAR2(20)\n" +
                "    , cust_last_name     VARCHAR2(20)\n" +
                "    , cust_address       CUST_ADDRESS_TYP\n" +
                "    , phone_numbers      PHONE_LIST_TYP\n" +
                "    , nls_language       VARCHAR2(3)\n" +
                "    , nls_territory      VARCHAR2(30)\n" +
                "    , credit_limit       NUMBER(9,2)\n" +
                "    , cust_email         VARCHAR2(30)\n" +
                "    , cust_orders        ORDER_LIST_TYP\n" +
                "    ) ;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TYPE customer_typ_demo AS OBJECT\n" +
                "    ( customer_id        NUMBER(6)\n" +
                "    , cust_first_name    VARCHAR2(20)\n" +
                "    , cust_last_name     VARCHAR2(20)\n" +
                "    , cust_address       CUST_ADDRESS_TYP\n" +
                "    , phone_numbers      PHONE_LIST_TYP\n" +
                "    , nls_language       VARCHAR2(3)\n" +
                "    , nls_territory      VARCHAR2(30)\n" +
                "    , credit_limit       NUMBER(9,2)\n" +
                "    , cust_email         VARCHAR2(30)\n" +
                "    , cust_orders        ORDER_LIST_TYP\n" +
                "    ) ;", format);
    }


    @Test
    public void test_1() {
        String sql = "CREATE TYPE data_typ1 AS OBJECT \n" +
                "   ( year NUMBER, \n" +
                "     MEMBER FUNCTION prod(invent NUMBER) RETURN NUMBER \n" +
                "   ); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TYPE data_typ1 AS OBJECT \n" +
                "   ( year NUMBER, \n" +
                "     MEMBER FUNCTION prod(invent NUMBER) RETURN NUMBER \n" +
                "   ); ", format);
    }
}
