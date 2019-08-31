package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-15.
 */
public class MySQLSelectTest_11_For_Update {

    @Test
    public void test_0() {
        String sql = "SELECT * FROM t1 WHERE c1 = (SELECT c1 FROM t2) FOR UPDATE;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "WHERE c1 = (\n" +
                "\t\tSELECT c1\n" +
                "\t\tFROM t2\n" +
                "\t)\n" +
                "FOR UPDATE;", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT * FROM t1 WHERE c1 = (SELECT c1 FROM t2 FOR UPDATE) FOR UPDATE;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "WHERE c1 = (\n" +
                "\t\tSELECT c1\n" +
                "\t\tFROM t2\n" +
                "\t\tFOR UPDATE\n" +
                "\t)\n" +
                "FOR UPDATE;", format);
    }

    @Test
    public void test_2() {
        String sql = "SELECT * FROM parent WHERE NAME = 'Jones' FOR SHARE;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM parent\n" +
                "WHERE NAME = 'Jones'\n" +
                "FOR SHARE;", format);
    }


    @Test
    public void test_3() {
        String sql = "SELECT * FROM t WHERE i = 2 FOR UPDATE NOWAIT;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t\n" +
                "WHERE i = 2\n" +
                "FOR UPDATE NOWAIT;", format);
    }

    @Test
    public void test_4() {
        String sql = "SELECT * FROM t FOR UPDATE SKIP LOCKED;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t\n" +
                "FOR UPDATE SKIP LOCKED;", format);
    }



}
