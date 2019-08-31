package com.gumihoy.sql.bvt.dialect.oracle.ddl.package_.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreatePackageTest_1_Associative_Array {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE PACKAGE aa_pkg AUTHID DEFINER IS\n" +
                "  TYPE aa_type IS TABLE OF INTEGER INDEX BY VARCHAR2(15);\n" +
                "END;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE PACKAGE aa_pkg\n" +
                "\tAUTHID DEFINER\n" +
                "IS\n" +
                "\tTYPE aa_type IS TABLE OF INTEGER INDEX BY VARCHAR2(15);\n" +
                "END;", format);
    }


}
