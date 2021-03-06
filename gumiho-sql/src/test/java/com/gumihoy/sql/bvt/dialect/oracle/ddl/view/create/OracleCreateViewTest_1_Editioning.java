package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateViewTest_1_Editioning {

    @Test
    public void test_0() {
        String sql = "CREATE EDITIONING VIEW ed_orders_view (o_id, o_date, o_status)\n" +
                "  AS SELECT order_id, order_date, order_status FROM orders\n" +
                "  WITH READ ONLY;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE EDITIONING VIEW ed_orders_view (o_id, o_date, o_status)\n" +
                "  AS SELECT order_id, order_date, order_status FROM orders\n" +
                "  WITH READ ONLY;", format);
    }

}
