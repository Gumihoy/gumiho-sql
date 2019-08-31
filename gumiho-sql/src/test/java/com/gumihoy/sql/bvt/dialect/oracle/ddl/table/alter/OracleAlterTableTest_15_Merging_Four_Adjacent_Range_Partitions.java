package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_15_Merging_Four_Adjacent_Range_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE sales\n" +
                "  MERGE PARTITIONS sales_q1_2000 TO sales_q4_2000\n" +
                "  INTO PARTITION sales_all_2000;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales\n" +
                "  MERGE PARTITIONS sales_q1_2000 TO sales_q4_2000\n" +
                "  INTO PARTITION sales_all_2000;", format);
    }

}
