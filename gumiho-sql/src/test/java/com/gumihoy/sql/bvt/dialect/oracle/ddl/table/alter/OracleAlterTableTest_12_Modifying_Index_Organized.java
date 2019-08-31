package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_12_Modifying_Index_Organized {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE countries_demo INITRANS 4;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE countries_demo INITRANS 4;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE countries_demo ADD OVERFLOW;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE countries_demo ADD OVERFLOW;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE countries_demo OVERFLOW INITRANS 4;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE countries_demo OVERFLOW INITRANS 4;", format);
    }

}
