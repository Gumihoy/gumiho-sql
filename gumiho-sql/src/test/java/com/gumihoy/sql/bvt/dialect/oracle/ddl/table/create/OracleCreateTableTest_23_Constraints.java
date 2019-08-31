package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_23_Constraints {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE persons OF person\n" +
                "  ( homeaddress NOT NULL,\n" +
                "      UNIQUE (homeaddress.phone),\n" +
                "      CHECK (homeaddress.zip IS NOT NULL),\n" +
                "      CHECK (homeaddress.city <> 'San Francisco') );";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE persons OF person (\n" +
                "\thomeaddress NOT NULL,\n" +
                "\tUNIQUE (homeaddress.phone),\n" +
                "\tCHECK (homeaddress.zip IS NOT NULL),\n" +
                "\tCHECK (homeaddress.city <> 'San Francisco')\n" +
                ");", format);
    }


}
