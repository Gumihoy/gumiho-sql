package com.gumihoy.sql.bvt.dialect.mysql.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLDeleteTest_6_Multi_Table {

    @Test
    public void test_0() {
        String sql = "DELETE t1, t2 FROM t1 INNER JOIN t2 INNER JOIN t3\n" +
                "WHERE t1.id=t2.id AND t2.id=t3.id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE t1, t2 FROM t1\n" +
                "\tINNER JOIN t2\n" +
                "\tINNER JOIN t3\n" +
                "WHERE t1.id = t2.id AND t2.id = t3.id;", format);
    }

    @Test
    public void test_1() {
        String sql = "DELETE FROM t1, t2 USING t1 INNER JOIN t2 INNER JOIN t3\n" +
                "WHERE t1.id=t2.id AND t2.id=t3.id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM t1, t2\n" +
                "\tUSING t1\n" +
                "\tINNER JOIN t2\n" +
                "\tINNER JOIN t3\n" +
                "WHERE t1.id = t2.id AND t2.id = t3.id;", format);
    }

    @Test
    public void test_2() {
        String sql = "DELETE t1 FROM t1 LEFT JOIN t2 ON t1.id=t2.id WHERE t2.id IS NULL;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE t1 FROM t1\n" +
                "\tLEFT JOIN t2 ON t1.id = t2.id\n" +
                "WHERE t2.id IS NULL;", format);
    }

    @Test
    public void test_3() {
        String sql = "DELETE a1, a2 FROM t1 AS a1 INNER JOIN t2 AS a2\n" +
                "WHERE a1.id=a2.id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE a1, a2 FROM t1 AS a1\n" +
                "\tINNER JOIN t2 AS a2\n" +
                "WHERE a1.id = a2.id;", format);
    }

    @Test
    public void test_4() {
        String sql = "DELETE FROM a1, a2 USING t1 AS a1 INNER JOIN t2 AS a2\n" +
                "WHERE a1.id=a2.id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM a1, a2\n" +
                "\tUSING t1 AS a1\n" +
                "\tINNER JOIN t2 AS a2\n" +
                "WHERE a1.id = a2.id;", format);
    }

}
