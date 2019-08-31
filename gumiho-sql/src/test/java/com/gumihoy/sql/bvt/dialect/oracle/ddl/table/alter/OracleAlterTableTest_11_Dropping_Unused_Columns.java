package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_11_Dropping_Unused_Columns {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE JOBS_TEMP SET UNUSED (DUMMY1, DUMMY2);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_TEMP SET UNUSED (DUMMY1, DUMMY2);", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE JOBS_TEMP DROP UNUSED COLUMNS;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_TEMP DROP UNUSED COLUMNS;", format);
    }

}
