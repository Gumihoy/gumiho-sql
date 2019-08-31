package com.gumihoy.sql.bvt.dialect.oracle.datatype.xml;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class OracleXmlDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 XMLTYPE ,\n" +
                "  c2 SYS.XMLTYPE,\n" +
                "  c3 URITYPE,\n" +
                "  c4 SYS.URITYPE\n" +
                ");";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 XMLTYPE,\n" +
                "\tc2 SYS.XMLTYPE,\n" +
                "\tc3 URITYPE,\n" +
                "\tc4 SYS.URITYPE\n" +
                ");", format);
    }
}
