package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_1_Parallel_Recovery {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE\n" +
                "   RECOVER TABLESPACE tbs_03\n" +
                "   PARALLEL;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE\n" +
                "   RECOVER TABLESPACE tbs_03\n" +
                "   PARALLEL;", format);
    }

}
