package com.gumihoy.sql.bvt.dialect.oracle.ddl.package_.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterPackageTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "ALTER PACKAGE emp_mgmt COMPILE PACKAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER PACKAGE emp_mgmt COMPILE PACKAGE;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER PACKAGE hr.emp_mgmt COMPILE BODY;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER PACKAGE hr.emp_mgmt COMPILE BODY;", format);
    }
}
