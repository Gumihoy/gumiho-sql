package com.gumihoy.sql.bvt.dialect.oracle.datatype.interval;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class OracleIntervalDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 INTERVAL YEAR  to month,\n" +
                "  c2 INTERVAL YEAR(2)  to month,\n" +
                "  c3 INTERVAL day  to second,\n" +
                "  c4 INTERVAL day(2)  to second,\n" +
                "  c5 INTERVAL day(2)  to second(3)\n" +
                ");";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 INTERVAL YEAR TO MONTH,\n" +
                "\tc2 INTERVAL YEAR (2) TO MONTH,\n" +
                "\tc3 INTERVAL DAY TO SECOND,\n" +
                "\tc4 INTERVAL DAY (2) TO SECOND,\n" +
                "\tc5 INTERVAL DAY (2) TO SECOND (3)\n" +
                ");", format);
    }
}
