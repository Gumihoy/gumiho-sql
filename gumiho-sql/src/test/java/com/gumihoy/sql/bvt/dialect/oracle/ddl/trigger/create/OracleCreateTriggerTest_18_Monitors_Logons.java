package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_18_Monitors_Logons {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER check_user\n" +
                "  AFTER LOGON ON DATABASE\n" +
                "  BEGIN\n" +
                "    check_user;\n" +
                "  EXCEPTION\n" +
                "    WHEN OTHERS THEN\n" +
                "      RAISE_APPLICATION_ERROR\n" +
                "        (-20000, 'Unexpected error: '|| DBMS_Utility.Format_Error_Stack);\n" +
                " END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER check_user\n" +
                "\tAFTER\n" +
                "\t\tLOGON\n" +
                "\tON DATABASE\n" +
                "\tBEGIN\n" +
                "\t\tcheck_user;\n" +
                "\t\tEXCEPTION\n" +
                "\t\t\tWHEN OTHERS THEN\n" +
                "\t\t\t\tRAISE_APPLICATION_ERROR(-20000, 'Unexpected error: ' || DBMS_Utility.Format_Error_Stack);\n" +
                "\tEND;", format);
    }


}
