package com.gumihoy.sql.bvt.dialect.mysql.dml.insert;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-06.
 */
public class MySQLInsertTest_8_On_Duplicate_Key_Update {

    @Test
    public void test_0() {
        String sql = "INSERT INTO t1 (a,b,c) VALUES (1,2,3)\n" +
                "  ON DUPLICATE KEY UPDATE c=c+1;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO t1 (a, b, c)\n" +
                "VALUES (1, 2, 3)\n" +
                "ON DUPLICATE KEY UPDATE c = c + 1;", format);
    }

    @Test
    public void test_1() {
        String sql = "INSERT INTO t1 (a,b,c) VALUES (1,2,3),(4,5,6)\n" +
                "  ON DUPLICATE KEY UPDATE c=VALUES(a)+VALUES(b);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO t1 (a, b, c)\n" +
                "VALUES (1, 2, 3), (4, 5, 6)\n" +
                "ON DUPLICATE KEY UPDATE c = VALUES(a) + VALUES(b);", format);
    }

    @Test
    public void test_2() {
        String sql = "INSERT INTO t1 (a, b)\n" +
                "SELECT * FROM\n" +
                "  (SELECT c, d FROM t2\n" +
                "   UNION\n" +
                "   SELECT e, f FROM t3) AS dt\n" +
                "ON DUPLICATE KEY UPDATE b = b + c;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO t1 (a, b)\n" +
                "SELECT *\n" +
                "FROM (\n" +
                "\tSELECT c, d\n" +
                "\tFROM t2\n" +
                "\tUNION\n" +
                "\tSELECT e, f\n" +
                "\tFROM t3\n" +
                ") AS dt\n" +
                "ON DUPLICATE KEY UPDATE b = b + c;", format);
    }
}
