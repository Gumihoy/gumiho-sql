package com.gumihoy.sql.bvt.dialect.oracle.datatype.datetime;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class OracleDateTimeDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 DATE ,\n" +
                "  c2 TIMESTAMP,\n" +
                "  c3 TIMESTAMP(10),\n" +
                "  c4 TIMESTAMP(10) with time zone,\n" +
                "  c5 TIMESTAMP(10) with local time zone\n" +
                ");";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 DATE,\n" +
                "\tc2 TIMESTAMP,\n" +
                "\tc3 TIMESTAMP(10),\n" +
                "\tc4 TIMESTAMP(10) WITH TIME ZONE,\n" +
                "\tc5 TIMESTAMP(10) WITH LOCAL TIME ZONE\n" +
                ");", format);
    }
}
