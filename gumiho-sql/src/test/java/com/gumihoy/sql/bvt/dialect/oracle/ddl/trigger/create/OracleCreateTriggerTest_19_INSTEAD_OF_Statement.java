package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_19_INSTEAD_OF_Statement {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER t\n" +
                "  INSTEAD OF CREATE ON SCHEMA\n" +
                "  BEGIN\n" +
                "    EXECUTE IMMEDIATE 'CREATE TABLE T (n NUMBER, m NUMBER)';\n" +
                "  END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER t\n" +
                "\tINSTEAD OF\n" +
                "\t\tCREATE\n" +
                "\tON SCHEMA\n" +
                "\tBEGIN\n" +
                "\t\tEXECUTE IMMEDIATE 'CREATE TABLE T (n NUMBER, m NUMBER)';\n" +
                "\tEND;", format);
    }


}
