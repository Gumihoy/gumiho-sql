package com.gumihoy.sql.bvt.dialect.oracle.ddl.function.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleDropFunctionTest_0_Simple {

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
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE FUNCTION get_bal(acc_no IN NUMBER) \n" +
                "   RETURN NUMBER \n" +
                "   IS acc_bal NUMBER(11,2);\n" +
                "   BEGIN \n" +
                "      SELECT order_total \n" +
                "      INTO acc_bal \n" +
                "      FROM orders \n" +
                "      WHERE customer_id = acc_no; \n" +
                "      RETURN(acc_bal); \n" +
                "    END;\n" +
                "/", format);
    }


}
