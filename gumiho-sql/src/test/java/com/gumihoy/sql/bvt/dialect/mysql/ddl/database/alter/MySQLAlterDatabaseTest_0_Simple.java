package com.gumihoy.sql.bvt.dialect.mysql.ddl.database.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLAlterDatabaseTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE sample CHARACTER SET utf8 COLLATE latin1_general_cs";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE sample\n" +
                "\tCHARACTER SET utf8\n" +
                "\tCOLLATE latin1_general_cs", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER DATABASE sample DEFAULT CHARACTER SET = utf8 COLLATE = latin1_general_cs";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE sample\n" +
                "\tDEFAULT CHARACTER SET = utf8\n" +
                "\tCOLLATE = latin1_general_cs", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER DATABASE `#mysql50#a-b-c` UPGRADE DATA DIRECTORY NAME;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE `#mysql50#a-b-c`\n" +
                "\tUPGRADE DATA DIRECTORY NAME;", format);
    }
}
