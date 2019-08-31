package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://dev.mysql.com/doc/refman/8.0/en/window-functions-named-windows.html
 *
 * @author kent on 2019-07-03.
 */
public class MySQLSelectTest_7_Window {

    @Test
    public void test_0() {
        String sql = "SELECT val\n" +
                "FROM numbers\n" +
                "WINDOW w AS (ORDER BY val);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT val\n" +
                "FROM numbers\n" +
                "WINDOW w AS (ORDER BY val);", format);
    }


    @Test
    public void test_1() {
        String sql = "SELECT\n" +
                "  DISTINCT year, country\n" +
                "FROM sales\n" +
                "WINDOW w AS (PARTITION BY country);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT DISTINCT year, country\n" +
                "FROM sales\n" +
                "WINDOW w AS (PARTITION BY country);", format);
    }

    @Test
    public void test_2() {
        String sql = "SELECT\n" +
                "         time, subject, val\n" +
                "       FROM observations\n" +
                "       WINDOW w AS (PARTITION BY subject ORDER BY time\n" +
                "                    ROWS UNBOUNDED PRECEDING);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT time, subject, val\n" +
                "FROM observations\n" +
                "WINDOW w AS (PARTITION BY subject ORDER BY time ROWS UNBOUNDED PRECEDING);", format);
    }
}
