package com.gumihoy.sql.bvt.dialect.oracle.ddl.synonym.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateSynonymTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE SYNONYM offices \n" +
                "   FOR hr.locations;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SYNONYM offices FOR hr.locations;", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE PUBLIC SYNONYM emp_table \n" +
                "   FOR hr.employees@remote.us.example.com;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE PUBLIC SYNONYM emp_table FOR hr.employees@remote.us.example.com;", format);
    }

    @Test
    public void test_2() {
        String sql = "CREATE PUBLIC SYNONYM customers FOR oe.customers;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE PUBLIC SYNONYM customers FOR oe.customers;", format);
    }


}
