package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_30_Modifying_Table_Columns {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE countries\n" +
                "   MODIFY (duty_pct NUMBER(3,2)); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE countries\n" +
                "   MODIFY (duty_pct NUMBER(3,2)); ", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE employees \n" +
                "   PCTFREE 30\n" +
                "   PCTUSED 60;  ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees \n" +
                "   PCTFREE 30\n" +
                "   PCTUSED 60; ", format);
    }
}
