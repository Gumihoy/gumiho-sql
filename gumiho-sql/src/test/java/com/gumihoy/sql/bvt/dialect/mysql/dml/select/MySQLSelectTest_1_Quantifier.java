package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class MySQLSelectTest_1_Quantifier {

    @Test
    public void test_0() {
        String sql = "SELECT ALL id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT ALL id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT DISTINCT id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT DISTINCT id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_2() {
        String sql = "SELECT DISTINCTROW id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT DISTINCTROW id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_3() {
        String sql = "SELECT HIGH_PRIORITY id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT HIGH_PRIORITY id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_4() {
        String sql = "SELECT STRAIGHT_JOIN id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT STRAIGHT_JOIN id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_5() {
        String sql = "SELECT SQL_SMALL_RESULT id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT SQL_SMALL_RESULT id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_6() {
        String sql = "SELECT SQL_BIG_RESULT id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT SQL_BIG_RESULT id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_7() {
        String sql = "SELECT SQL_BUFFER_RESULT id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT SQL_BUFFER_RESULT id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_8() {
        String sql = "SELECT SQL_NO_CACHE id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT SQL_NO_CACHE id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_9() {
        String sql = "SELECT SQL_CALC_FOUND_ROWS id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT SQL_CALC_FOUND_ROWS id\n" +
                "FROM employee", format);
    }

    @Test
    public void test_10() {
        String sql = "SELECT SQL_NO_CACHE id FROM employee";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT SQL_NO_CACHE id\n" +
                "FROM employee", format);
    }
}
