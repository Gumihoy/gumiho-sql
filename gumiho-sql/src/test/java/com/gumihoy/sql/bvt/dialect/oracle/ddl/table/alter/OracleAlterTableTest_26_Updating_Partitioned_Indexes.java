package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_26_Updating_Partitioned_Indexes {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE costs\n" +
                "  SPLIT PARTITION costs_q4_2003 at\n" +
                "    (TO_DATE('01-Nov-2003','dd-mon-yyyy')) \n" +
                "    INTO (PARTITION c_p1, PARTITION c_p2) \n" +
                "  UPDATE INDEXES (cost_ix (PARTITION c_p1 tablespace tbs_02, \n" +
                "                           PARTITION c_p2 tablespace tbs_03));";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE costs\n" +
                "  SPLIT PARTITION costs_q4_2003 at\n" +
                "    (TO_DATE('01-Nov-2003','dd-mon-yyyy')) \n" +
                "    INTO (PARTITION c_p1, PARTITION c_p2) \n" +
                "  UPDATE INDEXES (cost_ix (PARTITION c_p1 tablespace tbs_02, \n" +
                "                           PARTITION c_p2 tablespace tbs_03));", format);
    }

}
