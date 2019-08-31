package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_32_Adding_Altering_Renaming_Dropping_Table {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE JOBS_Temp MODIFY(JOB_TITLE VARCHAR2(100));";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_Temp MODIFY(JOB_TITLE VARCHAR2(100));", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE JOBS_Temp ADD (BONUS NUMBER (7,2), COMM NUMBER (5,2), DUMMY NUMBER(2));";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_Temp ADD (BONUS NUMBER (7,2), COMM NUMBER (5,2), DUMMY NUMBER(2));", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE JOBS_Temp RENAME COLUMN COMM TO COMMISSION;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_Temp RENAME COLUMN COMM TO COMMISSION;", format);
    }

    @Test
    public void test_3() {
        String sql = "ALTER TABLE JOBS_Temp DROP COLUMN DUMMY;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_Temp DROP COLUMN DUMMY;", format);
    }

    @Test
    public void test_4() {
        String sql = "ALTER TABLE JOBS_Temp DROP (BONUS, COMMISSION);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_Temp DROP (BONUS, COMMISSION);", format);
    }

}
