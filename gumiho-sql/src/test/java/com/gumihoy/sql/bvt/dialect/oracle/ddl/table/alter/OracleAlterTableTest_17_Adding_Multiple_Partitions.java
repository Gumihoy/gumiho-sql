package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_17_Adding_Multiple_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE print_media_part ADD\n" +
                "  PARTITION p3 values less than (300),\n" +
                "  PARTITION p4 values less than (400),\n" +
                "  PARTITION p5 values less than (500);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_part ADD\n" +
                "  PARTITION p3 values less than (300),\n" +
                "  PARTITION p4 values less than (400),\n" +
                "  PARTITION p5 values less than (500);", format);
    }

}
