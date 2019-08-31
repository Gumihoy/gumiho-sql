package com.gumihoy.sql.bvt.dialect.mysql.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLDeleteTest_0_Quantifier {

    @Test
    public void test_0() {
        String sql = "delete LOW_PRIORITY from employee ";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE LOW_PRIORITY FROM employee", format);
    }

    @Test
    public void test_1() {
        String sql = "delete QUICK from employee ";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE QUICK FROM employee", format);
    }

    @Test
    public void test_2() {
        String sql = "delete IGNORE from employee ";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE IGNORE FROM employee", format);
    }
}
