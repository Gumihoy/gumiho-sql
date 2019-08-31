package com.gumihoy.sql.bvt.dialect.mysql.ddl.table;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-26.
 */
public class MySQLCreateTableTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE new_tbl LIKE orig_tbl;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE new_tbl LIKE orig_tbl;", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE TABLE new_tbl AS SELECT * FROM orig_tbl;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE new_tbl\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM orig_tbl;", format);
    }

    @Test
    public void test_2() {
        String sql = "CREATE TABLE t (c CHAR(20) CHARACTER SET utf8 COLLATE utf8_bin);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE new_tbl LIKE orig_tbl;", format);
    }

    @Test
    public void test_3() {
        String sql = "CREATE TABLE test (blob_col BLOB, INDEX(blob_col(10)));";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE new_tbl LIKE orig_tbl;", format);
    }

    @Test
    public void test_4() {
        String sql = "SELECT * FROM tbl_name WHERE auto_col IS NULL;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE new_tbl LIKE orig_tbl;", format);
    }
}
