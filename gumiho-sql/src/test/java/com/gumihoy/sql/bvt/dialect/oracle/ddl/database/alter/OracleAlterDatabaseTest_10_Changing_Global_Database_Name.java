package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_10_Changing_Global_Database_Name {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE  \n" +
                "    RENAME GLOBAL_NAME TO demo.world.example.com; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE  \n" +
                "    RENAME GLOBAL_NAME TO demo.world.example.com; ", format);
    }

}

