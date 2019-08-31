package com.gumihoy.sql.bvt.dialect.oracle.ddl.role.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterRoleTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "ALTER ROLE warehouse_user NOT IDENTIFIED;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER ROLE warehouse_user\n" +
                "\tNOT IDENTIFIED;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER ROLE dw_manager \n" +
                "   IDENTIFIED BY data; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER ROLE dw_manager\n" +
                "\tIDENTIFIED BY data;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER ROLE dw_manager IDENTIFIED USING hr.admin;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER ROLE dw_manager\n" +
                "\tIDENTIFIED USING hr.admin;", format);
    }
}
