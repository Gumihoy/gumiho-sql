package com.gumihoy.sql.bvt.dialect.mysql.ddl.schema.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLCreateSchemaTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE schema sample";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SCHEMA sample", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE SCHEMA IF NOT EXISTS sample";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SCHEMA IF NOT EXISTS sample", format);
    }
}
