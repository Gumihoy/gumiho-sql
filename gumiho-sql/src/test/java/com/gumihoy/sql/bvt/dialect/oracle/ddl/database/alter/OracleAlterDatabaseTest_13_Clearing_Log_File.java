package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_13_Clearing_Log_File {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE  \n" +
                "    CLEAR LOGFILE 'diskc:log3.log';";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE  \n" +
                "    CLEAR LOGFILE 'diskc:log3.log';", format);
    }

}

