package com.gumihoy.sql.bvt.dialect.oracle.ddl.index.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterIndexTest_12_Visible {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE JOBS_Temp ADD CONSTRAINT chk_sal_min CHECK (MIN_SALARY >=2010);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_Temp\n" +
                "\tADD CONSTRAINT chk_sal_min CHECK (MIN_SALARY >= 2010);", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE xwarehouses \n" +
                "   ADD (PRIMARY KEY(XMLDATA.\"WarehouseID\"));";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE xwarehouses\n" +
                "\tADD (PRIMARY KEY (XMLDATA.\"WarehouseID\"));", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE client_tab ADD UNIQUE (pet_id);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE client_tab\n" +
                "\tADD UNIQUE (pet_id);", format);
    }

    @Test
    public void test_3() {
        String sql = "ALTER TABLE deptemps ADD (SCOPE FOR (COLUMN_VALUE) IS emptab); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE deptemps\n" +
                "\tADD (SCOPE FOR (COLUMN_VALUE) IS emptab);", format);
    }

    @Test
    public void test_4() {
        String sql = "ALTER TABLE deptemps ADD (REF(column_value) WITH ROWID); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE deptemps\n" +
                "\tADD (REF (column_value) WITH ROWID);", format);
    }

    @Test
    public void test_5() {
        String sql = "ALTER TABLE staff \n" +
                "    ADD (SCOPE FOR (dept) IS offices); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE staff\n" +
                "\tADD (SCOPE FOR (dept) IS offices);", format);
    }
}
