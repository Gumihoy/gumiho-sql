package com.gumihoy.sql.bvt.dialect.mysql.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class MySQLBoolLiteralTest {

    @Test
    public void test_0() {
        String sql = "SELECT TRUE, true, FALSE, false;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT TRUE, TRUE, FALSE, FALSE;", format);
    }
}
