package com.gumihoy.sql.bvt.dialect.mysql.dml.insert;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-06.
 */
public class MySQLInsertTest_7_SubQuery {

    @Test
    public void test_0() {
        String sql = "INSERT INTO tbl_temp2 (fld_id)\n" +
                "  SELECT tbl_temp1.fld_order_id\n" +
                "  FROM tbl_temp1 WHERE tbl_temp1.fld_order_id > 100;\n";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO tbl_temp2 (fld_id)\n" +
                "SELECT tbl_temp1.fld_order_id\n" +
                "FROM tbl_temp1\n" +
                "WHERE tbl_temp1.fld_order_id > 100;", format);
    }


    @Test
    public void test_1() {
        String sql = "INSERT INTO t1 (a, b)\n" +
                "  SELECT c, d FROM t2\n" +
                "  UNION\n" +
                "  SELECT e, f FROM t3\n" +
                "ON DUPLICATE KEY UPDATE b = b + c;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO t1 (a, b)\n" +
                "SELECT c, d\n" +
                "FROM t2\n" +
                "UNION\n" +
                "SELECT e, f\n" +
                "FROM t3\n" +
                "ON DUPLICATE KEY UPDATE b = b + c;", format);
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
