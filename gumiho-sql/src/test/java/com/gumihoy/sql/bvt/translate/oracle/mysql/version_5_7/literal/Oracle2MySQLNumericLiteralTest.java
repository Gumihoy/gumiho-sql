package com.gumihoy.sql.bvt.translate.oracle.mysql.version_5_7.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class Oracle2MySQLNumericLiteralTest {


    @Test
    public void test_0() {
        String sql = "select 1, .2, 3.4, -5, -6.78, +9.10, 1.2E3, 1.2E-3, -1.2E3, -1.2E-3, 1.0f, 2.0d";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 1, .2, 3.4, -5, -6.78, +9.10, 1.2E3, 1.2E-3, -1.2E3, -1.2E-3, 1.0F, 2.0D", format);
    }

}
