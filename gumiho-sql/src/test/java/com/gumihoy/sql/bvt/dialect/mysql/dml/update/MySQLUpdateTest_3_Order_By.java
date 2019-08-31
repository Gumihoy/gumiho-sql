package com.gumihoy.sql.bvt.dialect.mysql.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLUpdateTest_3_Order_By {

    @Test
    public void test_0() {
        String sql = "UPDATE t SET id = id + 1 ORDER BY id DESC;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE t\n" +
                "SET id = id + 1\n" +
                "ORDER BY id DESC;", format);
    }


}
