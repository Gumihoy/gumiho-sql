package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_16_BEFORE_Statement {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER drop_trigger\n" +
                "  BEFORE DROP ON hr.SCHEMA\n" +
                "  BEGIN\n" +
                "    RAISE_APPLICATION_ERROR (\n" +
                "      num => -20000,\n" +
                "      msg => 'Cannot drop object');\n" +
                "  END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER drop_trigger\n" +
                "  BEFORE DROP ON hr.SCHEMA\n" +
                "  BEGIN\n" +
                "    RAISE_APPLICATION_ERROR (\n" +
                "      num => -20000,\n" +
                "      msg => 'Cannot drop object');\n" +
                "  END;\n" +
                "/", format);
    }


}
