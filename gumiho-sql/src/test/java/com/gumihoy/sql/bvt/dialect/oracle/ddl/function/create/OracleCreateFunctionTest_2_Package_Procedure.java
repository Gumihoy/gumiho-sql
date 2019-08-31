package com.gumihoy.sql.bvt.dialect.oracle.ddl.function.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateFunctionTest_2_Package_Procedure {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE FUNCTION text_length(a CLOB) \n" +
                "   RETURN NUMBER DETERMINISTIC IS\n" +
                "BEGIN \n" +
                "  RETURN DBMS_LOB.GETLENGTH(a);\n" +
                "END;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE FUNCTION text_length (\n" +
                "\ta CLOB\n" +
                ") RETURN NUMBER\n" +
                "DETERMINISTIC\n" +
                "IS\n" +
                "\tBEGIN\n" +
                "\t\tRETURN DBMS_LOB.GETLENGTH(a);\n" +
                "\tEND;", format);
    }


}
