package com.gumihoy.sql.bvt.dialect.mysql.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-26.
 */
public class MySQLCreateViewTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE OR REPLACE VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_2() {
        String sql = "CREATE ALGORITHM = UNDEFINED VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE ALGORITHM = UNDEFINED VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_3() {
        String sql = "CREATE DEFINER = user VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DEFINER = user VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_4() {
        String sql = "CREATE SQL SECURITY DEFINER  VIEW test.v AS SELECT * FROM t;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SQL SECURITY DEFINER VIEW test.v\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM t;", format);
    }

    @Test
    public void test_5() {
        String sql = "CREATE VIEW v_today (today) AS SELECT CURRENT_DATE;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW v_today (\n" +
                "\ttoday\n" +
                ")\n" +
                "AS\n" +
                "\tSELECT CURRENT_DATE;", format);
    }

    @Test
    public void test_6() {
        String sql = "CREATE VIEW v AS SELECT qty, price, qty*price AS value FROM t WITH CASCADED CHECK OPTION;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW v\n" +
                "AS\n" +
                "\tSELECT qty, price, qty * price AS value\n" +
                "\tFROM t\n" +
                "\tWITH CASCADED CHECK OPTION;", format);
    }

}
