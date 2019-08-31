package com.gumihoy.sql.bvt.dialect.mysql.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class MySQLDataTimeLiteralTest {

    @Test
    public void test_0() {
        String sql = "select date '2012-12-31', time '09:26:50.124', TIMESTAMP '1997-01-31 09:26:50.124';";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT DATE '2012-12-31', TIME '09:26:50.124',\n" +
                "\tTIMESTAMP '1997-01-31 09:26:50.124';", format);
    }

}
