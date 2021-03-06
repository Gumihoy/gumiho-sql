package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleDropDatabaseTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "drop database;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP DATABASE;", format);
    }


}
