package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLCharacterUtils;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-15.
 */
public class MySQLSelectTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "SELECT 1 + 1;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 1 + 1;", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT 1 + 1 FROM DUAL;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 1 + 1\n" +
                "FROM DUAL;", format);
    }

    @Test
    public void test_2() {
        String sql = "SELECT CONCAT(last_name,', ',first_name) AS full_name\n" +
                "  FROM mytable ORDER BY full_name;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT CONCAT(last_name, ', ', first_name) AS full_name\n" +
                "FROM mytable\n" +
                "ORDER BY full_name;", format);
    }

    @Test
    public void test_3() {
        String sql = "SELECT CONCAT(last_name,', ',first_name) full_name\n" +
                "  FROM mytable ORDER BY full_name;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT CONCAT(last_name, ', ', first_name) full_name\n" +
                "FROM mytable\n" +
                "ORDER BY full_name;", format);
    }

    @Test
    public void test_4() {
        String sql = "SELECT t1.name, t2.salary FROM employee AS t1, info AS t2\n" +
                "  WHERE t1.name = t2.name;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT t1.name, t2.salary\n" +
                "FROM employee AS t1, info AS t2\n" +
                "WHERE t1.name = t2.name;", format);
    }

    @Test
    public void test_5() {
        String sql = "SELECT t1.name, t2.salary FROM employee t1, info t2\n" +
                "  WHERE t1.name = t2.name;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT t1.name, t2.salary\n" +
                "FROM employee t1, info t2\n" +
                "WHERE t1.name = t2.name;", format);
    }

    @Test
    public void test_6() {
        String sql = "SELECT t1.name, t2.salary FROM employee , info \n" +
                "  WHERE t1.name = t2.name;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT t1.name, t2.salary\n" +
                "FROM employee, info\n" +
                "WHERE t1.name = t2.name;", format);
    }
}
