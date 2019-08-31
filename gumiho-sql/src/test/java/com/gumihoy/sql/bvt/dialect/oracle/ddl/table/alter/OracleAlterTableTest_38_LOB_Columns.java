package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_38_LOB_Columns {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE employees ADD (resume CLOB)\n" +
                "  LOB (resume) STORE AS resume_seg (TABLESPACE example);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees ADD (resume CLOB)\n" +
                "  LOB (resume) STORE AS resume_seg (TABLESPACE example);", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE employees MODIFY LOB (resume) (CACHE); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees MODIFY LOB (resume) (CACHE); ", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE employees ADD (resume CLOB)\n" +
                "LOB (resume) STORE AS SECUREFILE resume_seg (TABLESPACE auto_seg_ts);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees ADD (resume CLOB)\n" +
                "LOB (resume) STORE AS SECUREFILE resume_seg (TABLESPACE auto_seg_ts);", format);
    }

}
