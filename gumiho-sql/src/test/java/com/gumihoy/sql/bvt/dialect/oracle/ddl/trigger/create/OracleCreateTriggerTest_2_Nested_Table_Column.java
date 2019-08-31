package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_2_Nested_Table_Column {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER dept_emplist_tr\n" +
                "  INSTEAD OF INSERT ON NESTED TABLE emplist OF dept_view\n" +
                "  REFERENCING NEW AS Employee\n" +
                "              PARENT AS Department\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  -- Insert on nested table translates to insert on base table:\n" +
                "  INSERT INTO employees (\n" +
                "    employee_id,\n" +
                "    last_name,\n" +
                "    email,\n" +
                "    hire_date,\n" +
                "    job_id,\n" +
                "    salary,\n" +
                "    department_id\n" +
                "  )\n" +
                "  VALUES (\n" +
                "    :Employee.emp_id,                      -- employee_id\n" +
                "    :Employee.lastname,                    -- last_name\n" +
                "    :Employee.lastname || '@example.com',  -- email\n" +
                "    SYSDATE,                               -- hire_date\n" +
                "    :Employee.job,                         -- job_id\n" +
                "    :Employee.sal,                         -- salary\n" +
                "    :Department.department_id              -- department_id\n" +
                "  );\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER dept_emplist_tr\n" +
                "  INSTEAD OF INSERT ON NESTED TABLE emplist OF dept_view\n" +
                "  REFERENCING NEW AS Employee\n" +
                "              PARENT AS Department\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  -- Insert on nested table translates to insert on base table:\n" +
                "  INSERT INTO employees (\n" +
                "    employee_id,\n" +
                "    last_name,\n" +
                "    email,\n" +
                "    hire_date,\n" +
                "    job_id,\n" +
                "    salary,\n" +
                "    department_id\n" +
                "  )\n" +
                "  VALUES (\n" +
                "    :Employee.emp_id,                      -- employee_id\n" +
                "    :Employee.lastname,                    -- last_name\n" +
                "    :Employee.lastname || '@example.com',  -- email\n" +
                "    SYSDATE,                               -- hire_date\n" +
                "    :Employee.job,                         -- job_id\n" +
                "    :Employee.sal,                         -- salary\n" +
                "    :Department.department_id              -- department_id\n" +
                "  );\n" +
                "END;\n" +
                "/", format);
    }


}
