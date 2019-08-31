package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_4_Dropping_Log_File_Members {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE\n" +
                "    DROP LOGFILE MEMBER 'diskb:log3.log'; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE\n" +
                "    DROP LOGFILE MEMBER 'diskb:log3.log'; ", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER DATABASE DROP LOGFILE GROUP 3; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE DROP LOGFILE GROUP 3; ", format);
    }
}

