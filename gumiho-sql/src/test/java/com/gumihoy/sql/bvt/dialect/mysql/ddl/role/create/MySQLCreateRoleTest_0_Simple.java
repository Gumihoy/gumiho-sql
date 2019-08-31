package com.gumihoy.sql.bvt.dialect.mysql.ddl.role.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLCreateRoleTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE ROLE 'administrator', 'developer';";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE ROLE 'administrator', 'developer';", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE ROLE 'webapp'@'localhost';";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE ROLE 'webapp'@'localhost';", format);
    }

}
