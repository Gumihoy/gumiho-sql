package com.gumihoy.sql.bvt.dialect.oracle.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleUpdateTest_1_Partition {

    @Test
    public void test_0() {
        String sql = "UPDATE sales PARTITION (sales_q1_1999) s\n" +
                "   SET s.promo_id = 494\n" +
                "   WHERE amount_sold > 1000;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE sales PARTITION (sales_q1_1999) s\n" +
                "SET s.promo_id = 494\n" +
                "WHERE amount_sold > 1000;", format);
    }

}
