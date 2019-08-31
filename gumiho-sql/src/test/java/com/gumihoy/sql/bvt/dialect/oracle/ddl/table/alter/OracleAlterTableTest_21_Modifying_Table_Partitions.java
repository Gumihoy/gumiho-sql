package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_21_Modifying_Table_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE list_customers MODIFY PARTITION asia \n" +
                "   UNUSABLE LOCAL INDEXES;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE list_customers MODIFY PARTITION asia \n" +
                "   UNUSABLE LOCAL INDEXES;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE list_customers MODIFY PARTITION asia\n" +
                "   REBUILD UNUSABLE LOCAL INDEXES;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE list_customers MODIFY PARTITION asia\n" +
                "   REBUILD UNUSABLE LOCAL INDEXES;", format);
    }
}
