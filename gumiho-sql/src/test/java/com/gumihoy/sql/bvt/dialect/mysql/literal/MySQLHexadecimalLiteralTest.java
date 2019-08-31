package com.gumihoy.sql.bvt.dialect.mysql.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class MySQLHexadecimalLiteralTest {

    @Test
    public void test_0() {
        String sql = "SELECT X'4D7953514C', CHARSET(X'4D7953514C');";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT X'4D7953514C', CHARSET(X'4D7953514C');", format);
    }
}
