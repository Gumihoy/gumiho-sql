package com.gumihoy.sql.bvt.dialect.oracle.ddl.index.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterIndexTest_20_Modify_Partition {

    @Test
    public void test_0() {
        String sql = "ALTER INDEX cost_ix\n" +
                "   MODIFY PARTITION p2 DEALLOCATE UNUSED;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY PARTITION p2\n" +
                "\t\tDEALLOCATE UNUSED;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER INDEX cost_ix  MODIFY PARTITION p2 ALLOCATE EXTENT;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY PARTITION p2\n" +
                "\t\tALLOCATE EXTENT;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER INDEX cost_ix  MODIFY PARTITION p2 PARAMETERS ('xx');";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY PARTITION p2\n" +
                "\t\tPARAMETERS ('xx');", format);
    }

    @Test
    public void test_3() {
        String sql = "ALTER INDEX cost_ix MODIFY PARTITION p2 COALESCE CLEANUP;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY PARTITION p2\n" +
                "\t\tCOALESCE CLEANUP;", format);
    }

    @Test
    public void test_4() {
        String sql = "ALTER INDEX cost_ix  MODIFY PARTITION p2 UPDATE BLOCK REFERENCES;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY PARTITION p2\n" +
                "\t\tUPDATE BLOCK REFERENCES;", format);
    }

    @Test
    public void test_5() {
        String sql = "ALTER INDEX cost_ix  MODIFY PARTITION p2 UNUSABLE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY PARTITION p2\n" +
                "\t\tUNUSABLE;", format);
    }
}
