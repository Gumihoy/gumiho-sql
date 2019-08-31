package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_4_Compound_Mutating_Table {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER Check_Employee_Salary_Raise\n" +
                "  FOR UPDATE OF Salary ON Employees\n" +
                "COMPOUND TRIGGER\n" +
                "  Ten_Percent                 CONSTANT NUMBER := 0.1;\n" +
                "  TYPE Salaries_t             IS TABLE OF Employees.Salary%TYPE;\n" +
                "  Avg_Salaries                Salaries_t;\n" +
                "  TYPE Department_IDs_t       IS TABLE OF Employees.Department_ID%TYPE;\n" +
                "  Department_IDs              Department_IDs_t;\n" +
                "\n" +
                "  -- Declare collection type and variable:\n" +
                "\n" +
                "  TYPE Department_Salaries_t  IS TABLE OF Employees.Salary%TYPE\n" +
                "                                INDEX BY VARCHAR2(80);\n" +
                "  Department_Avg_Salaries     Department_Salaries_t;\n" +
                "\n" +
                "  BEFORE STATEMENT IS\n" +
                "  BEGIN\n" +
                "    SELECT               AVG(e.Salary), NVL(e.Department_ID, -1)\n" +
                "      BULK COLLECT INTO  Avg_Salaries, Department_IDs\n" +
                "      FROM               Employees e\n" +
                "      GROUP BY           e.Department_ID;\n" +
                "    FOR j IN 1..Department_IDs.COUNT() LOOP\n" +
                "      Department_Avg_Salaries(Department_IDs(j)) := Avg_Salaries(j);\n" +
                "    END LOOP;\n" +
                "  END BEFORE STATEMENT;\n" +
                "\n" +
                "  AFTER EACH ROW IS\n" +
                "  BEGIN\n" +
                "    IF :NEW.Salary - :Old.Salary >\n" +
                "      Ten_Percent*Department_Avg_Salaries(:NEW.Department_ID)\n" +
                "    THEN\n" +
                "      Raise_Application_Error(-20000, 'Raise too big');\n" +
                "    END IF;\n" +
                "  END AFTER EACH ROW;\n" +
                "END Check_Employee_Salary_Raise;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER Check_Employee_Salary_Raise\n" +
                "\tFOR\n" +
                "\t\tUPDATE OF Salary\n" +
                "\tON Employees\n" +
                "\tCOMPOUND TRIGGER\n" +
                "\t\tTen_Percent CONSTANT NUMBER := 0.1;\n" +
                "\t\tTYPE Salaries_t IS TABLE OF Employees.Salary%TYPE;\n" +
                "\t\tAvg_Salaries Salaries_t;\n" +
                "\t\tTYPE Department_IDs_t IS TABLE OF Employees.Department_ID%TYPE;\n" +
                "\t\tDepartment_IDs Department_IDs_t;\n" +
                "\t\tTYPE Department_Salaries_t IS TABLE OF Employees.Salary%TYPE INDEX BY VARCHAR2(80);\n" +
                "\t\tDepartment_Avg_Salaries Department_Salaries_t;\n" +
                "\t\tBEFORE STATEMENT IS BEGIN\n" +
                "\t\t\tSELECT AVG(e.Salary), NVL(e.Department_ID, -1) BULK COLLECT\n" +
                "\t\t\t\tINTO Avg_Salaries, Department_IDs\n" +
                "\t\t\tFROM Employees e\n" +
                "\t\t\tGROUP BY e.Department_ID;\n" +
                "\t\t\tFOR j IN 1 .. Department_IDs.COUNT() LOOP\n" +
                "\t\t\t\tDepartment_Avg_Salaries(Department_IDs(j)) := Avg_Salaries(j);END LOOP;\n" +
                "\t\tEND BEFORE STATEMENT;\n" +
                "\t\tAFTER EACH ROW IS BEGIN\n" +
                "\t\t\tIF :NEW.Salary - :OLD.Salary > Ten_Percent * Department_Avg_Salaries(:NEW.Department_ID) THEN\n" +
                "\t\t\t\tRaise_Application_Error(-20000, 'Raise too big');\n" +
                "\t\t\tEND IF;\n" +
                "\t\tEND AFTER EACH ROW;\n" +
                "\tEND Check_Employee_Salary_Raise;", format);
    }


}
