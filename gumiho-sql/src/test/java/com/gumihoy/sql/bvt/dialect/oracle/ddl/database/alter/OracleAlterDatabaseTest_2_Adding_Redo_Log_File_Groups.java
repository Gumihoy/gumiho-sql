package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_2_Adding_Redo_Log_File_Groups {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE\n" +
                "  ADD LOGFILE GROUP 3 \n" +
                "    ('diska:log3.log' ,  \n" +
                "     'diskb:log3.log') SIZE 50K;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE\n" +
                "  ADD LOGFILE GROUP 3 \n" +
                "    ('diska:log3.log' ,  \n" +
                "     'diskb:log3.log') SIZE 50K;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER DATABASE  \n" +
                "    ADD LOGFILE THREAD 5 GROUP 4  \n" +
                "        ('diska:log4.log', \n" +
                "         'diskb:log4:log'); ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE  \n" +
                "    ADD LOGFILE THREAD 5 GROUP 4  \n" +
                "        ('diska:log4.log', \n" +
                "         'diskb:log4:log'); ", format);
    }
}
