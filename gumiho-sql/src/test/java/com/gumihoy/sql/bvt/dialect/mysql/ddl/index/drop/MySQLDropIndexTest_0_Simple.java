package com.gumihoy.sql.bvt.dialect.mysql.ddl.index.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLDropIndexTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "DROP INDEX `PRIMARY` ON t;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP INDEX `PRIMARY` ON t;", format);
    }

    @Test
    public void test_1() {
        String sql = "DROP ROLE 'webapp'@'localhost';";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP ROLE 'webapp'@'localhost';", format);
    }
}
