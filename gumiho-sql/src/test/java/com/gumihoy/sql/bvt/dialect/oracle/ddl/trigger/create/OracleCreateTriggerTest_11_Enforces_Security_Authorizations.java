package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_11_Enforces_Security_Authorizations {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER Employee_permit_changes\n" +
                "  BEFORE INSERT OR DELETE OR UPDATE ON employees\n" +
                "DECLARE\n" +
                "  Dummy             INTEGER;\n" +
                "  Not_on_weekends   EXCEPTION;\n" +
                "  Nonworking_hours  EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT (Not_on_weekends, -4097);\n" +
                "  PRAGMA EXCEPTION_INIT (Nonworking_hours, -4099);\n" +
                "BEGIN\n" +
                "   -- Check for weekends:\n" +
                " \n" +
                "   IF (TO_CHAR(Sysdate, 'DAY') = 'SAT' OR\n" +
                "     TO_CHAR(Sysdate, 'DAY') = 'SUN') THEN\n" +
                "       RAISE Not_on_weekends;\n" +
                "   END IF;\n" +
                " \n" +
                "  -- Check for work hours (8am to 6pm):\n" +
                " \n" +
                "  IF (TO_CHAR(Sysdate, 'HH24') < 8 OR\n" +
                "    TO_CHAR(Sysdate, 'HH24') > 18) THEN\n" +
                "      RAISE Nonworking_hours;\n" +
                "  END IF;\n" +
                " \n" +
                "EXCEPTION\n" +
                "  WHEN Not_on_weekends THEN\n" +
                "    Raise_application_error(-20324,'Might not change '\n" +
                "      ||'employee table during the weekend');\n" +
                "  WHEN Nonworking_hours THEN\n" +
                "    Raise_application_error(-20326,'Might not change '\n" +
                "     ||'emp table during Nonworking hours');\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER Employee_permit_changes\n" +
                "  BEFORE INSERT OR DELETE OR UPDATE ON employees\n" +
                "DECLARE\n" +
                "  Dummy             INTEGER;\n" +
                "  Not_on_weekends   EXCEPTION;\n" +
                "  Nonworking_hours  EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT (Not_on_weekends, -4097);\n" +
                "  PRAGMA EXCEPTION_INIT (Nonworking_hours, -4099);\n" +
                "BEGIN\n" +
                "   -- Check for weekends:\n" +
                " \n" +
                "   IF (TO_CHAR(Sysdate, 'DAY') = 'SAT' OR\n" +
                "     TO_CHAR(Sysdate, 'DAY') = 'SUN') THEN\n" +
                "       RAISE Not_on_weekends;\n" +
                "   END IF;\n" +
                " \n" +
                "  -- Check for work hours (8am to 6pm):\n" +
                " \n" +
                "  IF (TO_CHAR(Sysdate, 'HH24') < 8 OR\n" +
                "    TO_CHAR(Sysdate, 'HH24') > 18) THEN\n" +
                "      RAISE Nonworking_hours;\n" +
                "  END IF;\n" +
                " \n" +
                "EXCEPTION\n" +
                "  WHEN Not_on_weekends THEN\n" +
                "    Raise_application_error(-20324,'Might not change '\n" +
                "      ||'employee table during the weekend');\n" +
                "  WHEN Nonworking_hours THEN\n" +
                "    Raise_application_error(-20326,'Might not change '\n" +
                "     ||'emp table during Nonworking hours');\n" +
                "END;\n" +
                "/", format);
    }


}
