package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseTest_7_Changing_Default_Temporary_Tablespace {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE \n" +
                "   DEFAULT TEMPORARY TABLESPACE tbs_05;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE \n" +
                "   DEFAULT TEMPORARY TABLESPACE tbs_05;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER DATABASE\n" +
                "   DEFAULT TEMPORARY TABLESPACE tbs_grp_01;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE\n" +
                "   DEFAULT TEMPORARY TABLESPACE tbs_grp_01;", format);
    }

}

