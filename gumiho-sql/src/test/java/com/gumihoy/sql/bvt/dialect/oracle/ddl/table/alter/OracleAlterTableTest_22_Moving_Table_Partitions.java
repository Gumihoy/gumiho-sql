package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_22_Moving_Table_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE print_media_part \n" +
                "   MOVE PARTITION p2b TABLESPACE omf_ts1;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_part \n" +
                "   MOVE PARTITION p2b TABLESPACE omf_ts1;", format);
    }

}
