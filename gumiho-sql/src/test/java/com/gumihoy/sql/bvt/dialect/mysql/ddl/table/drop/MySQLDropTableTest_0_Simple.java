package com.gumihoy.sql.bvt.dialect.mysql.ddl.table.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLDropTableTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "DROP TEMPORARY TABLE IF EXISTS emp1, emp2 RESTRICT";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP TEMPORARY TABLE IF EXISTS emp1, emp2 RESTRICT", format);
    }

    @Test
    public void test_1() {
        String sql = "DROP TEMPORARY TABLE IF EXISTS emp1, emp2 CASCADE";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP TEMPORARY TABLE IF EXISTS emp1, emp2 CASCADE", format);
    }
}
