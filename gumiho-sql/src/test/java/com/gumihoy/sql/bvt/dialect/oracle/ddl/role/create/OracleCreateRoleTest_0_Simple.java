package com.gumihoy.sql.bvt.dialect.oracle.ddl.role.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateRoleTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE ROLE dw_manager;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE ROLE dw_manager;", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE ROLE dw_manager\n" +
                "   IDENTIFIED BY warehouse; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE ROLE dw_manager\n" +
                "\tIDENTIFIED BY warehouse;", format);
    }

    @Test
    public void test_2() {
        String sql = "CREATE ROLE warehouse_user IDENTIFIED GLOBALLY;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE ROLE warehouse_user\n" +
                "\tIDENTIFIED GLOBALLY;", format);
    }

    @Test
    public void test_3() {
        String sql = "CREATE ROLE warehouse_user IDENTIFIED EXTERNALLY;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE ROLE warehouse_user\n" +
                "\tIDENTIFIED EXTERNALLY;", format);
    }

    @Test
    public void test_4() {
        String sql = "CREATE ROLE role1 CONTAINER = CURRENT;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE ROLE role1\n" +
                "\tCONTAINER = CURRENT;", format);
    }

}
