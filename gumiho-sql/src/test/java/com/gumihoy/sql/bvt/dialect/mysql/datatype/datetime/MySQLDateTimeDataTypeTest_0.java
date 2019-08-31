package com.gumihoy.sql.bvt.dialect.mysql.datatype.datetime;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLDateTimeDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 DATE ,\n" +
                "  c2 DATETIME,\n" +
                "  c3 DATETIME('YYYY-MM-DD hh:mm:ss[.fraction]') ,\n" +
                "  c4 TIMESTAMP,\n" +
                "  c5 TIME,\n" +
                "  c6 YEAR\n" +
                ");";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 DATE,\n" +
                "\tc2 DATETIME,\n" +
                "\tc3 DATETIME('YYYY-MM-DD hh:mm:ss[.fraction]'),\n" +
                "\tc4 TIMESTAMP,\n" +
                "\tc5 TIME,\n" +
                "\tc6 YEAR\n" +
                ");", format);
    }
}
