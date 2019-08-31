package com.gumihoy.sql.bvt.dialect.mysql.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class MySQLBitValueLiteralTest {

    @Test
    public void test_0() {
        String sql = "SELECT b'1000001', CHARSET(b'1000001');";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT B'1000001', CHARSET(B'1000001');", format);
    }
}
