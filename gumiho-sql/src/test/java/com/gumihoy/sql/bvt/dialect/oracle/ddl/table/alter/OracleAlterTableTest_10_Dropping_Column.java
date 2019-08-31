package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_10_Dropping_Column {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE t1 DROP (pk);" +
                "ALTER TABLE t1 DROP (c1);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 DROP (pk);" +
                "ALTER TABLE t1 DROP (c1);", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE t1 DROP (pk) CASCADE CONSTRAINTS;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 DROP (pk) CASCADE CONSTRAINTS;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE t1 DROP (pk, fk, c1);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 DROP (pk, fk, c1);", format);
    }

}
