package com.gumihoy.sql.bvt.dialect.mysql.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLDeleteTest_2_Partition {

    @Test
    public void test_0() {
        String sql = "DELETE FROM sales PARTITION (sales_q1_1998)\n" +
                "   WHERE amount_sold > 1000;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM sales PARTITION (sales_q1_1998)\n" +
                "WHERE amount_sold > 1000;", format);
    }

}
