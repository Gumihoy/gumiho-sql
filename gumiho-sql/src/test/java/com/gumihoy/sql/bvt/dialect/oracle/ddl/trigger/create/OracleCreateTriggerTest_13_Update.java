package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_13_Update {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER log_salary_increase\n" +
                "  AFTER UPDATE OF salary ON employees\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  INSERT INTO Emp_log (Emp_id, Log_date, New_salary, Action)\n" +
                "  VALUES (:NEW.employee_id, SYSDATE, :NEW.salary, 'New Salary');\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER log_salary_increase\n" +
                "  AFTER UPDATE OF salary ON employees\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  INSERT INTO Emp_log (Emp_id, Log_date, New_salary, Action)\n" +
                "  VALUES (:NEW.employee_id, SYSDATE, :NEW.salary, 'New Salary');\n" +
                "END;\n" +
                "/", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE OR REPLACE TRIGGER trg1\n" +
                "  BEFORE UPDATE ON tab1\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  DBMS_OUTPUT.PUT_LINE('Old value of CLOB column: '||:OLD.c1);\n" +
                "  DBMS_OUTPUT.PUT_LINE('Proposed new value of CLOB column: '||:NEW.c1);\n" +
                "\n" +
                "  :NEW.c1 := :NEW.c1 || TO_CLOB('<hr><p>Standard footer paragraph.');\n" +
                "\n" +
                "  DBMS_OUTPUT.PUT_LINE('Final value of CLOB column: '||:NEW.c1);\n" +
                "END;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER trg1\n" +
                "  BEFORE UPDATE ON tab1\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  DBMS_OUTPUT.PUT_LINE('Old value of CLOB column: '||:OLD.c1);\n" +
                "  DBMS_OUTPUT.PUT_LINE('Proposed new value of CLOB column: '||:NEW.c1);\n" +
                "\n" +
                "  :NEW.c1 := :NEW.c1 || TO_CLOB('<hr><p>Standard footer paragraph.');\n" +
                "\n" +
                "  DBMS_OUTPUT.PUT_LINE('Final value of CLOB column: '||:NEW.c1);\n" +
                "END;", format);
    }

}
