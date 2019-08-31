package com.gumihoy.sql.bvt.dialect.oracle.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleSelectTest_14_Join {

    @Test
    public void test_0() {
        String sql = "SELECT * FROM\n" +
                "(SELECT EXTRACT(YEAR FROM order_date) year, order_mode, order_total FROM orders)\n" +
                "PIVOT\n" +
                "(SUM(order_total) FOR order_mode IN ('direct' AS Store, 'online' AS Internet))";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT * FROM\n" +
                "(SELECT EXTRACT(YEAR FROM order_date) year, order_mode, order_total FROM orders)\n" +
                "PIVOT\n" +
                "(SUM(order_total) FOR order_mode IN ('direct' AS Store, 'online' AS Internet))", format);
    }


    @Test
    public void test_1() {
        String sql = "SELECT * FROM pivot_table\n" +
                "  UNPIVOT (yearly_total FOR order_mode IN (store AS 'direct',\n" +
                "           internet AS 'online'))\n" +
                "  ORDER BY year, order_mode;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT * FROM pivot_table\n" +
                "  UNPIVOT (yearly_total FOR order_mode IN (store AS 'direct',\n" +
                "           internet AS 'online'))\n" +
                "  ORDER BY year, order_mode;", format);
    }


}
