package com.gumihoy.sql.bvt.dialect.oracle.ddl.index.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterIndexTest_25_Split_Partition {

    @Test
    public void test_0() {
        String sql = "ALTER INDEX cost_ix\n" +
                "   SPLIT PARTITION p2 AT (1500) \n" +
                "   INTO ( PARTITION p2a TABLESPACE tbs_01 LOGGING,\n" +
                "          PARTITION p2b TABLESPACE tbs_02);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tSPLIT PARTITION p2 AT (1500)\n" +
                "\t\tINTO (\n" +
                "\t\t\tPARTITION p2a\n" +
                "\t\t\t\tTABLESPACE tbs_01\n" +
                "\t\t\t\tLOGGING,\n" +
                "\t\t\tPARTITION p2b\n" +
                "\t\t\t\tTABLESPACE tbs_02\n" +
                "\t\t);", format);
    }

}
