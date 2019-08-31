package com.gumihoy.sql.bvt.dialect.oracle.ddl.index.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterIndexTest_24_Drop_Partition {

    @Test
    public void test_0() {
        String sql = "ALTER INDEX cost_ix\n" +
                "   DROP PARTITION p1;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX cost_ix\n" +
                "\tDROP PARTITION p1;", format);
    }

}
