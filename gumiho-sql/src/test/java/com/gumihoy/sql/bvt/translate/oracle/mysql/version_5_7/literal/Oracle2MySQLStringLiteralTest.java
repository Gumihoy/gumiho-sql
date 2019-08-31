package com.gumihoy.sql.bvt.translate.oracle.mysql.version_5_7.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class Oracle2MySQLStringLiteralTest {

    @Test
    public void test_0() {
        String sql = "select 'a string'";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'a string'", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT 'another string'";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'another string'", format);
    }

    @Test
    public void test_2() {
        String sql = "select 'a' ' ' 'string'";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'a string'", format);
    }

    @Test
    public void test_3() {
        String sql = "SELECT 'hello', '\"hello\"', '\"\"hello\"\"', 'hel''lo', '\\'hello';";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'hello', '\"hello\"', '\"\"hello\"\"', 'hel\\'lo', '\\'hello';", format);
    }


    @Test
    public void test_4() {
        String sql = "SELECT 'This\\nIs\\nFour\\nLines';";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'This\\nIs\\nFour\\nLines';", format);
    }

    @Test
    public void test_5() {
        String sql = "SELECT 'disappearing\\ backslash';";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'disappearing\\ backslash';", format);
    }

}
