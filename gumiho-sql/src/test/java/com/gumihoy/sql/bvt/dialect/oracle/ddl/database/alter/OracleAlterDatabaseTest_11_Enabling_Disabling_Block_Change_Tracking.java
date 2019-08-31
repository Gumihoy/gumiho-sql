package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_11_Enabling_Disabling_Block_Change_Tracking {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE\n" +
                "  ENABLE BLOCK CHANGE TRACKING\n" +
                "    USING FILE 'tracking_file' REUSE;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE\n" +
                "  ENABLE BLOCK CHANGE TRACKING\n" +
                "    USING FILE 'tracking_file' REUSE;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER DATABASE\n" +
                "  DISABLE BLOCK CHANGE TRACKING;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE\n" +
                "  DISABLE BLOCK CHANGE TRACKING;", format);
    }
}

