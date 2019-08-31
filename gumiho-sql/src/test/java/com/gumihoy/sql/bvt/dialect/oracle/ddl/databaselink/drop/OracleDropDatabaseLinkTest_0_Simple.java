package com.gumihoy.sql.bvt.dialect.oracle.ddl.databaselink.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleDropDatabaseLinkTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "drop DATABASE LINK remote";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP DATABASE LINK remote", format);
    }

    @Test
    public void test_1() {
        String sql = "DROP PUBLIC DATABASE LINK remote ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP PUBLIC DATABASE LINK remote", format);
    }


}
