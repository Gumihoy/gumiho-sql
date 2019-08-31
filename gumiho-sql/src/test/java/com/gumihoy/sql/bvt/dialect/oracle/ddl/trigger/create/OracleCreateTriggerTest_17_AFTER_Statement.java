package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_17_AFTER_Statement {

    @Test
    public void test_0() {
        String sql = "CREATE TRIGGER log_errors\n" +
                "  AFTER SERVERERROR ON DATABASE\n" +
                "  BEGIN\n" +
                "    IF (IS_SERVERERROR (1017)) THEN\n" +
                "      NULL;  -- (substitute code that processes logon error)\n" +
                "    ELSE\n" +
                "      NULL;  -- (substitute code that logs error code)\n" +
                "    END IF;\n" +
                "  END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TRIGGER log_errors\n" +
                "  AFTER SERVERERROR ON DATABASE\n" +
                "  BEGIN\n" +
                "    IF (IS_SERVERERROR (1017)) THEN\n" +
                "      NULL;  -- (substitute code that processes logon error)\n" +
                "    ELSE\n" +
                "      NULL;  -- (substitute code that logs error code)\n" +
                "    END IF;\n" +
                "  END;\n" +
                "/", format);
    }


}
