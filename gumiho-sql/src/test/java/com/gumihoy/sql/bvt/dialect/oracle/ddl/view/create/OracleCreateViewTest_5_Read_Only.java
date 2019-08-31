package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateViewTest_5_Read_Only {

    @Test
    public void test_0() {
        String sql = "CREATE VIEW customer_ro (name, language, credit)\n" +
                "      AS SELECT cust_last_name, nls_language, credit_limit\n" +
                "      FROM customers\n" +
                "      WITH READ ONLY;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW customer_ro (name, language, credit)\n" +
                "      AS SELECT cust_last_name, nls_language, credit_limit\n" +
                "      FROM customers\n" +
                "      WITH READ ONLY;", format);
    }

}
