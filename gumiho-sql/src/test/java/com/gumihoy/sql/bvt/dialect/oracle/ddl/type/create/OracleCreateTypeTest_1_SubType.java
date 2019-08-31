package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTypeTest_1_SubType {

    @Test
    public void test_0() {
        String sql = "CREATE TYPE corporate_customer_typ_demo UNDER customer_typ\n" +
                "    ( account_mgr_id     NUMBER(6)\n" +
                "    );";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TYPE corporate_customer_typ_demo UNDER customer_typ\n" +
                "    ( account_mgr_id     NUMBER(6)\n" +
                "    );", format);
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
