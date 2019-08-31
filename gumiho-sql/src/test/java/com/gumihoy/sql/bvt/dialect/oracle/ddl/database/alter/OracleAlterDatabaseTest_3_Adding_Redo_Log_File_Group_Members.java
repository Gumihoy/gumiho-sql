package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_3_Adding_Redo_Log_File_Group_Members {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE   \n" +
                "   ADD LOGFILE MEMBER 'diskc:log3.log'  \n" +
                "   TO GROUP 3;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE   \n" +
                "   ADD LOGFILE MEMBER 'diskc:log3.log'  \n" +
                "   TO GROUP 3;", format);
    }

}
