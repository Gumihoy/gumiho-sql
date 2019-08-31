package com.gumihoy.sql.bvt.dialect.oracle.ddl.function.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateFunctionTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE FUNCTION get_bal(acc_no IN NUMBER) \n" +
                "   RETURN NUMBER \n" +
                "   IS acc_bal NUMBER(11,2);\n" +
                "   BEGIN \n" +
                "      SELECT order_total \n" +
                "      INTO acc_bal \n" +
                "      FROM orders \n" +
                "      WHERE customer_id = acc_no; \n" +
                "      RETURN(acc_bal); \n" +
                "    END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE FUNCTION get_bal (\n" +
                "\tacc_no IN NUMBER\n" +
                ") RETURN NUMBER\n" +
                "IS\n" +
                "\tacc_bal NUMBER(11, 2);\n" +
                "\tBEGIN\n" +
                "\t\tSELECT order_total\n" +
                "\t\t\tINTO acc_bal\n" +
                "\t\tFROM orders\n" +
                "\t\tWHERE customer_id = acc_no;\n" +
                "\t\tRETURN (acc_bal);\n" +
                "\tEND;", format);
    }


}
