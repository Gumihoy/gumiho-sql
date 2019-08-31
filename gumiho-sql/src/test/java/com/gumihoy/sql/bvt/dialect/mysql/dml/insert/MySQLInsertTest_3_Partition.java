package com.gumihoy.sql.bvt.dialect.mysql.dml.insert;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-06.
 */
public class MySQLInsertTest_3_Partition {

    @Test
    public void test_0() {
        String sql = "INSERT INTO employees PARTITION (p2) VALUES (20, 'Jan', 'Jones', 1, 3);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO employees PARTITION (p2)\n" +
                "VALUES (20, 'Jan', 'Jones', 1, 3);", format);
    }


}
