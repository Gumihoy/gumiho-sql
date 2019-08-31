package com.gumihoy.sql.bvt.dialect.oracle.ddl.procedure.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateProcedureTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE PROCEDURE find_root\n" +
                "   ( x IN REAL ) \n" +
                "   IS LANGUAGE C\n" +
                "      NAME c_find_root\n" +
                "      LIBRARY c_utils\n" +
                "      PARAMETERS ( x BY REFERENCE );";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE PROCEDURE find_root (\n" +
                "\tx IN REAL\n" +
                ")\n" +
                "IS\n" +
                "\tLANGUAGE C NAME c_find_root LIBRARY c_utils PARAMETERS (x BY REFERENCE);", format);
    }


}
