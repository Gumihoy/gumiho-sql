package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_23_Renaming_Table_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE sales RENAME PARTITION sales_q4_2003 TO sales_currentq;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales\n" +
                "\tRENAME PARTITION sales_q4_2003 TO sales_currentq;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE sales RENAME PARTITION FOR (sales_q4_2003) TO sales_currentq;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales\n" +
                "\tRENAME PARTITION FOR (sales_q4_2003) TO sales_currentq;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE sales RENAME SUBPARTITION sales_q4_2003 TO sales_currentq;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales\n" +
                "\tRENAME SUBPARTITION sales_q4_2003 TO sales_currentq;", format);
    }

    @Test
    public void test_3() {
        String sql = "ALTER TABLE sales RENAME SUBPARTITION FOR (sales_q4_2003) TO sales_currentq;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales\n" +
                "\tRENAME SUBPARTITION (sales_q4_2003) TO sales_currentq;", format);
    }

}
