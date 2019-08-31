package com.gumihoy.sql.bvt.dialect.mysql.ddl.schema.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLCreateSchemaTest_1_Option {

    @Test
    public void test_0() {
        String sql = "CREATE SCHEMA sample CHARACTER SET utf8 COLLATE latin1_general_cs";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SCHEMA sample\n" +
                "\tCHARACTER SET utf8\n" +
                "\tCOLLATE latin1_general_cs", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE schema sample DEFAULT CHARACTER SET = utf8 COLLATE = latin1_general_cs";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SCHEMA sample\n" +
                "\tDEFAULT CHARACTER SET = utf8\n" +
                "\tCOLLATE = latin1_general_cs", format);
    }
}
