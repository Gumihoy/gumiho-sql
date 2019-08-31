package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_0_Read_Only_Write {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE OPEN READ ONLY;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE OPEN READ ONLY;", format);
    }


    @Test
    public void test_1() {
        String sql = "ALTER DATABASE OPEN READ WRITE RESETLOGS;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE OPEN READ WRITE RESETLOGS;", format);
    }
}
