package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_5_Foreign_Key {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER emp_dept_check\n" +
                "  BEFORE INSERT OR UPDATE OF Deptno ON emp\n" +
                "  FOR EACH ROW WHEN (NEW.Deptno IS NOT NULL)\n" +
                "\n" +
                "  -- Before row is inserted or DEPTNO is updated in emp table,\n" +
                "  -- fire this trigger to verify that new foreign key value (DEPTNO)\n" +
                "  -- is present in dept table.\n" +
                "DECLARE\n" +
                "  Dummy               INTEGER;  -- Use for cursor fetch\n" +
                "  Invalid_department  EXCEPTION;\n" +
                "  Valid_department    EXCEPTION;\n" +
                "  Mutating_table      EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT (Invalid_department, -4093);\n" +
                "  PRAGMA EXCEPTION_INIT (Valid_department, -4092);\n" +
                "  PRAGMA EXCEPTION_INIT (Mutating_table, -4091);\n" +
                "\n" +
                "  -- Cursor used to verify parent key value exists.\n" +
                "  -- If present, lock parent key's row so it cannot be deleted\n" +
                "  -- by another transaction until this transaction is\n" +
                "  -- committed or rolled back.\n" +
                "\n" +
                "  CURSOR Dummy_cursor (Dn NUMBER) IS\n" +
                "    SELECT Deptno FROM dept\n" +
                "    WHERE Deptno = Dn\n" +
                "    FOR UPDATE OF Deptno;\n" +
                "BEGIN\n" +
                "  OPEN Dummy_cursor (:NEW.Deptno);\n" +
                "  FETCH Dummy_cursor INTO Dummy;\n" +
                "\n" +
                "  -- Verify parent key.\n" +
                "  -- If not found, raise user-specified error code and message.\n" +
                "  -- If found, close cursor before allowing triggering statement to complete:\n" +
                "\n" +
                "  IF Dummy_cursor%NOTFOUND THEN\n" +
                "    RAISE Invalid_department;\n" +
                "  ELSE\n" +
                "    RAISE Valid_department;\n" +
                "  END IF;\n" +
                "  CLOSE Dummy_cursor;\n" +
                "EXCEPTION\n" +
                "  WHEN Invalid_department THEN\n" +
                "    CLOSE Dummy_cursor;\n" +
                "    Raise_application_error(-20000, 'Invalid Department'\n" +
                "      || ' Number' || TO_CHAR(:NEW.deptno));\n" +
                "  WHEN Valid_department THEN\n" +
                "    CLOSE Dummy_cursor;\n" +
                "  WHEN Mutating_table THEN\n" +
                "    NULL;\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER emp_dept_check\n" +
                "  BEFORE INSERT OR UPDATE OF Deptno ON emp\n" +
                "  FOR EACH ROW WHEN (NEW.Deptno IS NOT NULL)\n" +
                "\n" +
                "  -- Before row is inserted or DEPTNO is updated in emp table,\n" +
                "  -- fire this trigger to verify that new foreign key value (DEPTNO)\n" +
                "  -- is present in dept table.\n" +
                "DECLARE\n" +
                "  Dummy               INTEGER;  -- Use for cursor fetch\n" +
                "  Invalid_department  EXCEPTION;\n" +
                "  Valid_department    EXCEPTION;\n" +
                "  Mutating_table      EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT (Invalid_department, -4093);\n" +
                "  PRAGMA EXCEPTION_INIT (Valid_department, -4092);\n" +
                "  PRAGMA EXCEPTION_INIT (Mutating_table, -4091);\n" +
                "\n" +
                "  -- Cursor used to verify parent key value exists.\n" +
                "  -- If present, lock parent key's row so it cannot be deleted\n" +
                "  -- by another transaction until this transaction is\n" +
                "  -- committed or rolled back.\n" +
                "\n" +
                "  CURSOR Dummy_cursor (Dn NUMBER) IS\n" +
                "    SELECT Deptno FROM dept\n" +
                "    WHERE Deptno = Dn\n" +
                "    FOR UPDATE OF Deptno;\n" +
                "BEGIN\n" +
                "  OPEN Dummy_cursor (:NEW.Deptno);\n" +
                "  FETCH Dummy_cursor INTO Dummy;\n" +
                "\n" +
                "  -- Verify parent key.\n" +
                "  -- If not found, raise user-specified error code and message.\n" +
                "  -- If found, close cursor before allowing triggering statement to complete:\n" +
                "\n" +
                "  IF Dummy_cursor%NOTFOUND THEN\n" +
                "    RAISE Invalid_department;\n" +
                "  ELSE\n" +
                "    RAISE Valid_department;\n" +
                "  END IF;\n" +
                "  CLOSE Dummy_cursor;\n" +
                "EXCEPTION\n" +
                "  WHEN Invalid_department THEN\n" +
                "    CLOSE Dummy_cursor;\n" +
                "    Raise_application_error(-20000, 'Invalid Department'\n" +
                "      || ' Number' || TO_CHAR(:NEW.deptno));\n" +
                "  WHEN Valid_department THEN\n" +
                "    CLOSE Dummy_cursor;\n" +
                "  WHEN Mutating_table THEN\n" +
                "    NULL;\n" +
                "END;\n" +
                "/", format);
    }


}
