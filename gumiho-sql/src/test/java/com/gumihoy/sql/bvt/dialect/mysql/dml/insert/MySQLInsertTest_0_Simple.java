package com.gumihoy.sql.bvt.dialect.mysql.dml.insert;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLInsertTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "INSERT INTO Persons VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO Persons\n" +
                "VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')", format);
    }
}
