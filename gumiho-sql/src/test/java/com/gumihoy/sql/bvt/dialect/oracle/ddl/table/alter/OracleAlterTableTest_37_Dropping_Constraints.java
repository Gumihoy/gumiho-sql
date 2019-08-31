package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_37_Dropping_Constraints {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE departments \n" +
                "    DROP PRIMARY KEY CASCADE; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE departments \n" +
                "    DROP PRIMARY KEY CASCADE;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE departments\n" +
                "    DROP CONSTRAINT pk_dept CASCADE; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE departments\n" +
                "    DROP CONSTRAINT pk_dept CASCADE; ", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE employees \n" +
                "    DROP UNIQUE (email); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees \n" +
                "    DROP UNIQUE (email); ", format);
    }

}
