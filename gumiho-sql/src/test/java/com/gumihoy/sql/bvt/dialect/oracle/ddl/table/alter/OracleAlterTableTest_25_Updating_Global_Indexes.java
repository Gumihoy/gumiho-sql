package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_25_Updating_Global_Indexes {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE sales SPLIT PARTITION sales_q1_2000\n" +
                "   AT (TO_DATE('16-FEB-2000','DD-MON-YYYY'))\n" +
                "   INTO (PARTITION q1a_2000, PARTITION q1b_2000)\n" +
                "   UPDATE GLOBAL INDEXES;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales SPLIT PARTITION sales_q1_2000\n" +
                "   AT (TO_DATE('16-FEB-2000','DD-MON-YYYY'))\n" +
                "   INTO (PARTITION q1a_2000, PARTITION q1b_2000)\n" +
                "   UPDATE GLOBAL INDEXES;", format);
    }

}
