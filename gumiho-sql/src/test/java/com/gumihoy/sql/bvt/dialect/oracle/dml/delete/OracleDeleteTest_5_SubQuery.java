package com.gumihoy.sql.bvt.dialect.oracle.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-05.
 */
public class OracleDeleteTest_5_SubQuery {

    @Test
    public void test_0() {
        String sql = "DELETE (SELECT * FROM product_price_history) WHERE  currency_code = 'EUR';";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE (SELECT * FROM product_price_history) WHERE  currency_code = 'EUR';", format);
    }


    @Test
    public void test_1() {
        String sql = "DELETE product_price_history pp \n" +
                "WHERE  (product_id, currency_code, effective_from_date) \n" +
                "   IN (SELECT product_id, currency_code, Max(effective_from_date) \n" +
                "       FROM   product_price_history \n" +
                "       GROUP BY product_id, currency_code);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE product_price_history pp \n" +
                "WHERE  (product_id, currency_code, effective_from_date) \n" +
                "   IN (SELECT product_id, currency_code, Max(effective_from_date) \n" +
                "       FROM   product_price_history \n" +
                "       GROUP BY product_id, currency_code);", format);
    }

}
