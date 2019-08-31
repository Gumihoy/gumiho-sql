package com.gumihoy.sql.bvt.dialect.oracle.ddl.synonym.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterSynonymTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "ALTER SYNONYM offices COMPILE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER SYNONYM offices COMPILE;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER PUBLIC SYNONYM emp_table COMPILE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER PUBLIC SYNONYM emp_table COMPILE;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER SYNONYM offices NONEDITIONABLE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER SYNONYM offices NONEDITIONABLE;", format);
    }
}
