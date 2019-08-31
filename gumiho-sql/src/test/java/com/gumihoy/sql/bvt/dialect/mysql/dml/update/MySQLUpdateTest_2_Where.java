package com.gumihoy.sql.bvt.dialect.mysql.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLUpdateTest_2_Where {

    @Test
    public void test_0() {
        String sql = "UPDATE items,month SET items.price=month.price\n" +
                "WHERE items.id=month.id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE items, month\n" +
                "SET items.price = month.price\n" +
                "WHERE items.id = month.id;", format);
    }


}
