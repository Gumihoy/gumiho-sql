package com.gumihoy.sql.bvt.dialect.oracle.ddl.package_.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreatePackageTest_3 {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE PACKAGE emp_admin AUTHID DEFINER AS\n" +
                "  -- Declare public type, cursor, and exception:\n" +
                "  TYPE EmpRecTyp IS RECORD (emp_id NUMBER, sal NUMBER);\n" +
                "  CURSOR desc_salary RETURN EmpRecTyp;\n" +
                "  invalid_salary EXCEPTION;\n" +
                "\n" +
                "  -- Declare public subprograms:\n" +
                "\n" +
                "  FUNCTION hire_employee (\n" +
                "    last_name       VARCHAR2,\n" +
                "    first_name      VARCHAR2,\n" +
                "    email           VARCHAR2,\n" +
                "    phone_number    VARCHAR2,\n" +
                "    job_id          VARCHAR2,\n" +
                "    salary          NUMBER,\n" +
                "    commission_pct  NUMBER,\n" +
                "    manager_id      NUMBER,\n" +
                "    department_id   NUMBER\n" +
                "  ) RETURN NUMBER;\n" +
                "\n" +
                "  -- Overload preceding public subprogram:\n" +
                "  PROCEDURE fire_employee (emp_id NUMBER);\n" +
                "  PROCEDURE fire_employee (emp_email VARCHAR2);\n" +
                "\n" +
                "  PROCEDURE raise_salary (emp_id NUMBER, amount NUMBER);\n" +
                "  FUNCTION nth_highest_salary (n NUMBER) RETURN EmpRecTyp;\n" +
                "END emp_admin;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE PACKAGE emp_admin\n" +
                "\tAUTHID DEFINER\n" +
                "AS\n" +
                "\tTYPE EmpRecTyp IS RECORD (\n" +
                "\t\temp_id NUMBER,\n" +
                "\t\tsal NUMBER\n" +
                "\t);\n" +
                "\tCURSOR desc_salary RETURN EmpRecTyp;\n" +
                "\tinvalid_salary EXCEPTION;\n" +
                "\tFUNCTION hire_employee (\n" +
                "\t\tlast_name VARCHAR2,\n" +
                "\t\tfirst_name VARCHAR2,\n" +
                "\t\temail VARCHAR2,\n" +
                "\t\tphone_number VARCHAR2,\n" +
                "\t\tjob_id VARCHAR2,\n" +
                "\t\tsalary NUMBER,\n" +
                "\t\tcommission_pct NUMBER,\n" +
                "\t\tmanager_id NUMBER,\n" +
                "\t\tdepartment_id NUMBER\n" +
                "\t) RETURN NUMBER;\n" +
                "\tPROCEDURE fire_employee (\n" +
                "\t\temp_id NUMBER\n" +
                "\t);\n" +
                "\tPROCEDURE fire_employee (\n" +
                "\t\temp_email VARCHAR2\n" +
                "\t);\n" +
                "\tPROCEDURE raise_salary (\n" +
                "\t\temp_id NUMBER,\n" +
                "\t\tamount NUMBER\n" +
                "\t);\n" +
                "\tFUNCTION nth_highest_salary (\n" +
                "\t\tn NUMBER\n" +
                "\t) RETURN EmpRecTyp;\n" +
                "END emp_admin;", format);
    }


}
