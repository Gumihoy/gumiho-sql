package com.gumihoy.sql.bvt.dialect.oracle.ddl.databaselink.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateDatabaseLinkTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE DATABASE LINK remote \n" +
                "   USING 'remote'; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DATABASE LINK remote\n" +
                "\tUSING 'remote';", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE shared DATABASE LINK remote \n" +
                "   USING 'remote'; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SHARED DATABASE LINK remote\n" +
                "\tUSING 'remote';", format);
    }

    @Test
    public void test_2() {
        String sql = "CREATE PUBLIC DATABASE LINK remote \n" +
                "   USING 'remote'; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE PUBLIC DATABASE LINK remote\n" +
                "\tUSING 'remote';", format);
    }


    @Test
    public void test_3() {
        String sql = "CREATE shared PUBLIC DATABASE LINK remote \n" +
                "   USING 'remote'; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SHARED PUBLIC DATABASE LINK remote\n" +
                "\tUSING 'remote';", format);
    }


}
