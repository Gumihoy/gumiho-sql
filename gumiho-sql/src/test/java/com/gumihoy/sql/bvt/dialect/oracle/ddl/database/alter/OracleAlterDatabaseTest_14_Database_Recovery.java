package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_14_Database_Recovery {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE \n" +
                "  RECOVER AUTOMATIC DATABASE;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE \n" +
                "  RECOVER AUTOMATIC DATABASE;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER DATABASE \n" +
                "    RECOVER LOGFILE 'diskc:log3.log'; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE \n" +
                "    RECOVER LOGFILE 'diskc:log3.log'; ", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER DATABASE \n" +
                "    RECOVER AUTOMATIC UNTIL TIME '2001-10-27:14:00:00'; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE \n" +
                "    RECOVER AUTOMATIC UNTIL TIME '2001-10-27:14:00:00'; ", format);
    }
}

