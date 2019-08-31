package com.gumihoy.sql.bvt.dialect.oracle.comment;

import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCommentTest_1_Minus {

    @Test
    public void test_0() {
        String sql = "SELECT " +
                "       -- select the name\n" +
                "       last_name,                                   \n" +
                "       employee_id,                                 -- employee id\n" +
                "       salary + NVL(commission_pct, 0),             -- total compensation\n" +
                "       job_id,                                      -- job\n" +
                "       e.department_id                              -- and department\n" +
                "  FROM employees e,                                 -- of all employees\n" +
                "       departments d\n" +
                "  WHERE e.department_id = d.department_id\n" +
                "    AND salary + NVL(commission_pct, 0) >           -- whose compensation \n" +
                "                                                    -- is greater than\n" +
                "        (SELECT salary + NVL(commission_pct,0)      -- the compensation\n" +
                "          FROM employees \n" +
                "          WHERE last_name = 'Pataballa')            -- of Pataballa\n" +
                "  ORDER BY last_name,                               -- and order by last name\n" +
                "           employee_id                              -- and employee id.\n" +
                ";";
        System.out.println(sql);
        SQLOutputConfig config = new SQLOutputConfig();
        config.keepComment = true;
        String format = SQLUtils.format(sql, DBType.Oracle, config);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT last_name,                                   -- select the name\n" +
                "       employee_id,                                 -- employee id\n" +
                "       salary + NVL(commission_pct, 0),             -- total compensation\n" +
                "       job_id,                                      -- job\n" +
                "       e.department_id                              -- and department\n" +
                "  FROM employees e,                                 -- of all employees\n" +
                "       departments d\n" +
                "  WHERE e.department_id = d.department_id\n" +
                "    AND salary + NVL(commission_pct, 0) >           -- whose compensation \n" +
                "                                                    -- is greater than\n" +
                "        (SELECT salary + NVL(commission_pct,0)      -- the compensation\n" +
                "          FROM employees \n" +
                "          WHERE last_name = 'Pataballa')            -- of Pataballa\n" +
                "  ORDER BY last_name,                               -- and order by last name\n" +
                "           employee_id                              -- and employee id.\n" +
                ";", format);
    }


}
