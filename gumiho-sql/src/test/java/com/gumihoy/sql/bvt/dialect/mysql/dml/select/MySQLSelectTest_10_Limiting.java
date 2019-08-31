package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-15.
 */
public class MySQLSelectTest_10_Limiting {

    @Test
    public void test_0() {
        String sql = "SELECT * FROM tbl LIMIT 5,10;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM tbl\n" +
                "LIMIT 5, 10;", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT * FROM tbl LIMIT 95,18446744073709551615;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM tbl\n" +
                "LIMIT 95, 18446744073709551615;", format);
    }

    @Test
    public void test_2() {
        String sql = "SELECT *\n" +
                "FROM tbl\n" +
                "LIMIT 5;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM tbl\n" +
                "LIMIT 5;", format);
    }

    @Test
    public void test_3() {
        String sql = "SELECT * FROM tbl LIMIT 5 offset 10;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM tbl\n" +
                "LIMIT 5 OFFSET 10;", format);
    }
}
