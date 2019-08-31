package com.gumihoy.sql.bvt.dialect.oracle.ddl.package_.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreatePackageTest_2_Serially_Reusable {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE PACKAGE bodiless_pkg AUTHID DEFINER IS\n" +
                "  PRAGMA SERIALLY_REUSABLE;\n" +
                "  n NUMBER := 5;\n" +
                "END;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE PACKAGE bodiless_pkg\n" +
                "\tAUTHID DEFINER\n" +
                "IS\n" +
                "\tPRAGMA SERIALLY_REUSABLE;\n" +
                "\tn NUMBER := 5;\n" +
                "END;", format);
    }


}
