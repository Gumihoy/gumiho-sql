package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_10_Complex_Constraints {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER salary_check\n" +
                "  BEFORE INSERT OR UPDATE OF Sal, Job ON Emp\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "DECLARE\n" +
                "  Minsal               NUMBER;\n" +
                "  Maxsal               NUMBER;\n" +
                "  Salary_out_of_range  EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT (Salary_out_of_range, -4096);\n" +
                "\n" +
                "BEGIN\n" +
                "  /* Retrieve minimum & maximum salary for employee's new job classification\n" +
                "     from SALGRADE table into MINSAL and MAXSAL: */\n" +
                "\n" +
                "  SELECT Losal, Hisal INTO Minsal, Maxsal\n" +
                "  FROM Salgrade\n" +
                "  WHERE Job_classification = :NEW.Job;\n" +
                "\n" +
                "  /* If employee's new salary is less than or greater than\n" +
                "     job classification's limits, raise exception.\n" +
                "     Exception message is returned and pending INSERT or UPDATE statement\n" +
                "     that fired the trigger is rolled back: */\n" +
                "\n" +
                "  IF (:NEW.Sal < Minsal OR :NEW.Sal > Maxsal) THEN\n" +
                "    RAISE Salary_out_of_range;\n" +
                "  END IF;\n" +
                "EXCEPTION\n" +
                "  WHEN Salary_out_of_range THEN\n" +
                "    Raise_application_error (\n" +
                "      -20300,\n" +
                "      'Salary '|| TO_CHAR(:NEW.Sal) ||' out of range for '\n" +
                "      || 'job classification ' ||:NEW.Job\n" +
                "      ||' for employee ' || :NEW.Ename\n" +
                "    );\n" +
                "  WHEN NO_DATA_FOUND THEN\n" +
                "    Raise_application_error(-20322, 'Invalid Job Classification');\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER salary_check\n" +
                "  BEFORE INSERT OR UPDATE OF Sal, Job ON Emp\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "DECLARE\n" +
                "  Minsal               NUMBER;\n" +
                "  Maxsal               NUMBER;\n" +
                "  Salary_out_of_range  EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT (Salary_out_of_range, -4096);\n" +
                "\n" +
                "BEGIN\n" +
                "  /* Retrieve minimum & maximum salary for employee's new job classification\n" +
                "     from SALGRADE table into MINSAL and MAXSAL: */\n" +
                "\n" +
                "  SELECT Losal, Hisal INTO Minsal, Maxsal\n" +
                "  FROM Salgrade\n" +
                "  WHERE Job_classification = :NEW.Job;\n" +
                "\n" +
                "  /* If employee's new salary is less than or greater than\n" +
                "     job classification's limits, raise exception.\n" +
                "     Exception message is returned and pending INSERT or UPDATE statement\n" +
                "     that fired the trigger is rolled back: */\n" +
                "\n" +
                "  IF (:NEW.Sal < Minsal OR :NEW.Sal > Maxsal) THEN\n" +
                "    RAISE Salary_out_of_range;\n" +
                "  END IF;\n" +
                "EXCEPTION\n" +
                "  WHEN Salary_out_of_range THEN\n" +
                "    Raise_application_error (\n" +
                "      -20300,\n" +
                "      'Salary '|| TO_CHAR(:NEW.Sal) ||' out of range for '\n" +
                "      || 'job classification ' ||:NEW.Job\n" +
                "      ||' for employee ' || :NEW.Ename\n" +
                "    );\n" +
                "  WHEN NO_DATA_FOUND THEN\n" +
                "    Raise_application_error(-20322, 'Invalid Job Classification');\n" +
                "END;\n" +
                "/", format);
    }


}
