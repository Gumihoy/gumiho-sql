package com.gumihoy.sql.bvt.dialect.oracle.ddl.index.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterIndexTest_23_Rename_SubPartition {

    @Test
    public void test_0() {
        String sql = "ALTER index JOBS_Temp RENAME SUBPARTITION p3 TO p3_Q3;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX JOBS_Temp\n" +
                "\tRENAME SUBPARTITION p3 TO p3_Q3;", format);
    }


}
