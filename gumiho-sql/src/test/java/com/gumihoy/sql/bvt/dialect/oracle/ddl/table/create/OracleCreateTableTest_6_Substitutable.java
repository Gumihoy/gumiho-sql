package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_6_Substitutable {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE persons OF person_t;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE persons OF person_t;", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE TABLE books (title VARCHAR2(100), author person_t);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE books (title VARCHAR2(100), author person_t);", format);
    }

}
