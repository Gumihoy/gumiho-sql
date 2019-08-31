package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-15.
 */
public class MySQLSelectTest_9_Order_By {

    @Test
    public void test_0() {
        String sql = "(SELECT a FROM t1 WHERE a=10 AND B=1 ORDER BY a LIMIT 10)\n" +
                "UNION\n" +
                "(SELECT a FROM t2 WHERE a=11 AND B=2 ORDER BY a LIMIT 10);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("(\n" +
                "\tSELECT a\n" +
                "\tFROM t1\n" +
                "\tWHERE a = 10 AND B = 1\n" +
                "\tORDER BY a\n" +
                "\tLIMIT 10\n" +
                ")\n" +
                "UNION\n" +
                "(\n" +
                "\tSELECT a\n" +
                "\tFROM t2\n" +
                "\tWHERE a = 11 AND B = 2\n" +
                "\tORDER BY a\n" +
                "\tLIMIT 10\n" +
                ");", format);
    }

    @Test
    public void test_1() {
        String sql = "(SELECT a FROM t1 WHERE a=10 AND B=1)\n" +
                "UNION\n" +
                "(SELECT a FROM t2 WHERE a=11 AND B=2)\n" +
                "ORDER BY a LIMIT 10;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("(\n" +
                "\tSELECT a\n" +
                "\tFROM t1\n" +
                "\tWHERE a = 10 AND B = 1\n" +
                ")\n" +
                "UNION\n" +
                "(\n" +
                "\tSELECT a\n" +
                "\tFROM t2\n" +
                "\tWHERE a = 11 AND B = 2\n" +
                ")\n" +
                "ORDER BY a\n" +
                "LIMIT 10;", format);
    }

    @Test
    public void test_2() {
        String sql = "SELECT 1 AS foo UNION SELECT 2 ORDER BY MAX(1);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 1 AS foo\n" +
                "UNION\n" +
                "SELECT 2\n" +
                "ORDER BY MAX(1);", format);
    }

}
