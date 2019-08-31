package com.gumihoy.sql.bvt.dialect.mysql.dml.insert;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-06.
 */
public class MySQLInsertTest_1_Quantifier {

    @Test
    public void test_0() {
        String sql = "INSERT LOW_PRIORITY INTO Persons VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT LOW_PRIORITY INTO Persons\n" +
                "VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')", format);
    }


    @Test
    public void test_1() {
        String sql = "INSERT DELAYED INTO Persons VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT DELAYED INTO Persons\n" +
                "VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')", format);
    }


    @Test
    public void test_2() {
        String sql = "INSERT HIGH_PRIORITY INTO Persons VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT HIGH_PRIORITY INTO Persons\n" +
                "VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')", format);
    }

    @Test
    public void test_3() {
        String sql = "INSERT IGNORE INTO Persons VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT IGNORE INTO Persons\n" +
                "VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')", format);
    }
}
