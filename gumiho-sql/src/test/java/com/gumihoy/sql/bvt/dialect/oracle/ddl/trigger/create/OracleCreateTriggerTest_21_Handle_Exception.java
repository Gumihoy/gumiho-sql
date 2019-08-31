package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_21_Handle_Exception {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER employees_tr\n" +
                "  AFTER INSERT ON employees\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  -- When remote database is unavailable, compilation fails here:\n" +
                "  INSERT INTO employees@remote (\n" +
                "    employee_id, first_name, last_name, email, hire_date, job_id\n" +
                "  ) \n" +
                "  VALUES (\n" +
                "    99, 'Jane', 'Doe', 'jane.doe@example.com', SYSDATE, 'ST_MAN'\n" +
                "  );\n" +
                "EXCEPTION\n" +
                "  WHEN OTHERS THEN\n" +
                "    INSERT INTO emp_log (Emp_id, Log_date, New_salary, Action)\n" +
                "      VALUES (99, SYSDATE, NULL, 'Could not insert');\n" +
                "    RAISE;\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER employees_tr\n" +
                "  AFTER INSERT ON employees\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  -- When remote database is unavailable, compilation fails here:\n" +
                "  INSERT INTO employees@remote (\n" +
                "    employee_id, first_name, last_name, email, hire_date, job_id\n" +
                "  ) \n" +
                "  VALUES (\n" +
                "    99, 'Jane', 'Doe', 'jane.doe@example.com', SYSDATE, 'ST_MAN'\n" +
                "  );\n" +
                "EXCEPTION\n" +
                "  WHEN OTHERS THEN\n" +
                "    INSERT INTO emp_log (Emp_id, Log_date, New_salary, Action)\n" +
                "      VALUES (99, SYSDATE, NULL, 'Could not insert');\n" +
                "    RAISE;\n" +
                "END;\n" +
                "/", format);
    }


}
