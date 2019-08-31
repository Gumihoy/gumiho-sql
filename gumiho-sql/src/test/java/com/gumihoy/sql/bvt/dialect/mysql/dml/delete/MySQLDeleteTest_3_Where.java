package com.gumihoy.sql.bvt.dialect.mysql.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLDeleteTest_3_Where {

    @Test
    public void test_0() {
        String sql = "DELETE FROM product_price_history WHERE product_id = 3;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM product_price_history\n" +
                "WHERE product_id = 3;", format);
    }


}
