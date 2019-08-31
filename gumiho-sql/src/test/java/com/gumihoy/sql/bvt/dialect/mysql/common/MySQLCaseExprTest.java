package com.gumihoy.sql.bvt.dialect.mysql.common;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLCaseExprTest {

    @Test
    public void test_0() {
        String sql = "SELECT CASE 1 WHEN 1 THEN 'one' WHEN 2 THEN 'two' ELSE 'more' END;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT CASE 1\n" +
                "\t\tWHEN 1 THEN 'one'\n" +
                "\t\tWHEN 2 THEN 'two'\n" +
                "\t\tELSE 'more'\n" +
                "\tEND;", format);
    }

    @Test
    public void test_1() {
        String sql = " SELECT CASE WHEN 1>0 THEN 'true' ELSE 'false' END;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT CASE\n" +
                "\t\tWHEN 1 > 0 THEN 'true'\n" +
                "\t\tELSE 'false'\n" +
                "\tEND;", format);
    }

    @Test
    public void test_2() {
        String sql = " SELECT CASE BINARY 'B' WHEN 'a' THEN 1 WHEN 'b' THEN 2 END;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT B'1000001', CHARSET(B'1000001');", format);
    }
}
