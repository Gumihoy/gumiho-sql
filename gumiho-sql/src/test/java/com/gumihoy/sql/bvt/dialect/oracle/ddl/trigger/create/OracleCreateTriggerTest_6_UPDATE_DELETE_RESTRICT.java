package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_6_UPDATE_DELETE_RESTRICT {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER dept_restrict\n" +
                "  BEFORE DELETE OR UPDATE OF Deptno ON dept\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "  -- Before row is deleted from dept or primary key (DEPTNO) of dept is updated,\n" +
                "  -- check for dependent foreign key values in emp;\n" +
                "  -- if any are found, roll back.\n" +
                "\n" +
                "DECLARE\n" +
                "  Dummy                  INTEGER;  -- Use for cursor fetch\n" +
                "  employees_present      EXCEPTION;\n" +
                "  employees_not_present  EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT (employees_present, -4094);\n" +
                "  PRAGMA EXCEPTION_INIT (employees_not_present, -4095);\n" +
                "\n" +
                "  -- Cursor used to check for dependent foreign key values.\n" +
                "  CURSOR Dummy_cursor (Dn NUMBER) IS\n" +
                "    SELECT Deptno FROM emp WHERE Deptno = Dn;\n" +
                "\n" +
                "BEGIN\n" +
                "  OPEN Dummy_cursor (:OLD.Deptno);\n" +
                "  FETCH Dummy_cursor INTO Dummy;\n" +
                "\n" +
                "  -- If dependent foreign key is found, raise user-specified\n" +
                "  -- error code and message. If not found, close cursor\n" +
                "  -- before allowing triggering statement to complete.\n" +
                "\n" +
                "  IF Dummy_cursor%FOUND THEN\n" +
                "    RAISE employees_present;     -- Dependent rows exist\n" +
                "  ELSE\n" +
                "    RAISE employees_not_present; -- No dependent rows exist\n" +
                "  END IF;\n" +
                "  CLOSE Dummy_cursor;\n" +
                "\n" +
                "EXCEPTION\n" +
                "  WHEN employees_present THEN\n" +
                "    CLOSE Dummy_cursor;\n" +
                "    Raise_application_error(-20001, 'Employees Present in'\n" +
                "      || ' Department ' || TO_CHAR(:OLD.DEPTNO));\n" +
                "  WHEN employees_not_present THEN\n" +
                "    CLOSE Dummy_cursor;\n" +
                "END;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER dept_restrict\n" +
                "  BEFORE DELETE OR UPDATE OF Deptno ON dept\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "  -- Before row is deleted from dept or primary key (DEPTNO) of dept is updated,\n" +
                "  -- check for dependent foreign key values in emp;\n" +
                "  -- if any are found, roll back.\n" +
                "\n" +
                "DECLARE\n" +
                "  Dummy                  INTEGER;  -- Use for cursor fetch\n" +
                "  employees_present      EXCEPTION;\n" +
                "  employees_not_present  EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT (employees_present, -4094);\n" +
                "  PRAGMA EXCEPTION_INIT (employees_not_present, -4095);\n" +
                "\n" +
                "  -- Cursor used to check for dependent foreign key values.\n" +
                "  CURSOR Dummy_cursor (Dn NUMBER) IS\n" +
                "    SELECT Deptno FROM emp WHERE Deptno = Dn;\n" +
                "\n" +
                "BEGIN\n" +
                "  OPEN Dummy_cursor (:OLD.Deptno);\n" +
                "  FETCH Dummy_cursor INTO Dummy;\n" +
                "\n" +
                "  -- If dependent foreign key is found, raise user-specified\n" +
                "  -- error code and message. If not found, close cursor\n" +
                "  -- before allowing triggering statement to complete.\n" +
                "\n" +
                "  IF Dummy_cursor%FOUND THEN\n" +
                "    RAISE employees_present;     -- Dependent rows exist\n" +
                "  ELSE\n" +
                "    RAISE employees_not_present; -- No dependent rows exist\n" +
                "  END IF;\n" +
                "  CLOSE Dummy_cursor;\n" +
                "\n" +
                "EXCEPTION\n" +
                "  WHEN employees_present THEN\n" +
                "    CLOSE Dummy_cursor;\n" +
                "    Raise_application_error(-20001, 'Employees Present in'\n" +
                "      || ' Department ' || TO_CHAR(:OLD.DEPTNO));\n" +
                "  WHEN employees_not_present THEN\n" +
                "    CLOSE Dummy_cursor;\n" +
                "END;", format);
    }


}
