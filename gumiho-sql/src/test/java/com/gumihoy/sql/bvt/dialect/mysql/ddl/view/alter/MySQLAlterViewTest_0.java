package com.gumihoy.sql.bvt.dialect.mysql.ddl.view.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-26.
 */
public class MySQLAlterViewTest_0 {

    @Test
    public void test_0() {
        String sql = "alter VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_1() {
        String sql = "alter VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_2() {
        String sql = "alter ALGORITHM = UNDEFINED VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER ALGORITHM = UNDEFINED VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_3() {
        String sql = "alter DEFINER = user VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DEFINER = user VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_4() {
        String sql = "alter SQL SECURITY DEFINER  VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER SQL SECURITY DEFINER VIEW test.v\n" +
                "AS\n" +
                        "\tSELECT *\n" +
                        "\tFROM t;", format);
    }

    @Test
    public void test_5() {
        String sql = "alter VIEW v_today (today) AS SELECT CURRENT_DATE;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER VIEW v_today (\n" +
                "\ttoday\n" +
                ")\n" +
                "AS\n" +
                "\tSELECT CURRENT_DATE;", format);
    }

    @Test
    public void test_6() {
        String sql = "alter VIEW v AS SELECT qty, price, qty*price AS value FROM t WITH CASCADED CHECK OPTION;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER VIEW v\n" +
                "AS\n" +
                "\tSELECT qty, price, qty * price AS value\n" +
                "\tFROM t\n" +
                "\tWITH CASCADED CHECK OPTION;", format);
    }

}
