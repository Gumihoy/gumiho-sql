package com.gumihoy.sql.bvt.dialect.mysql.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class MySQLNullLiteralTest {

    @Test
    public void test_0() {
        String sql = "SELECT NULL, 1+NULL, CONCAT('Invisible',NULL);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT NULL, 1 + NULL, CONCAT('Invisible', NULL);", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT * FROM my_table WHERE phone = NULL;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM my_table\n" +
                "WHERE phone = NULL;", format);
    }
}
