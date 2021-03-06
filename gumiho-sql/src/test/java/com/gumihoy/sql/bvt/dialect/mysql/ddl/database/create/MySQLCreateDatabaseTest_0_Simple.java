package com.gumihoy.sql.bvt.dialect.mysql.ddl.database.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLCreateDatabaseTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE DATABASE sample";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DATABASE sample", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE DATABASE IF NOT EXISTS sample";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DATABASE IF NOT EXISTS sample", format);
    }
}
