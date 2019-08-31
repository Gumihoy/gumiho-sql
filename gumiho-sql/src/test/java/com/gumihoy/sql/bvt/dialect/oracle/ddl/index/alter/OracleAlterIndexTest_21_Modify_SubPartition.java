package com.gumihoy.sql.bvt.dialect.oracle.ddl.index.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterIndexTest_21_Modify_SubPartition {

    @Test
    public void test_0() {
        String sql = "ALTER INDEX cost_ix MODIFY SUBPARTITION p3 UNUSABLE";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY SUBPARTITION p3 UNUSABLE", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER INDEX cost_ix MODIFY SUBPARTITION p3 ALLOCATE EXTENT";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY SUBPARTITION p3 ALLOCATE EXTENT", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER INDEX cost_ix MODIFY SUBPARTITION p3 DEALLOCATE UNUSED;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tMODIFY SUBPARTITION p3 DEALLOCATE UNUSED;", format);
    }


}
