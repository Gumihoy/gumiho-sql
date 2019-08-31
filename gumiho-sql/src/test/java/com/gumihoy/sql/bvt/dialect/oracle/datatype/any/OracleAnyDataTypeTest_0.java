package com.gumihoy.sql.bvt.dialect.oracle.datatype.any;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class OracleAnyDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 ANYDATA ,\n" +
                "  c2 SYS.ANYDATA,\n" +
                "  c3 ANYTYPE,\n" +
                "  c4 SYS.ANYTYPE,\n" +
                "  c5 ANYDATASET,\n" +
                "  c6 SYS.ANYDATASET\n" +
                ");";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 ANYDATA,\n" +
                "\tc2 SYS.ANYDATA,\n" +
                "\tc3 ANYTYPE,\n" +
                "\tc4 SYS.ANYTYPE,\n" +
                "\tc5 ANYDATASET,\n" +
                "\tc6 SYS.ANYDATASET\n" +
                ");", format);
    }
}
