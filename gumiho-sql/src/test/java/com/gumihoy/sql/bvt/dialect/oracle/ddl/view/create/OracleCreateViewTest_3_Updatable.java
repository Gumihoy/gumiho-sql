package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateViewTest_3_Updatable {

    @Test
    public void test_0() {
        String sql = "CREATE VIEW clerk AS\n" +
                "   SELECT employee_id, last_name, department_id, job_id \n" +
                "   FROM employees\n" +
                "   WHERE job_id = 'PU_CLERK' \n" +
                "      or job_id = 'SH_CLERK' \n" +
                "      or job_id = 'ST_CLERK';";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW clerk AS\n" +
                "   SELECT employee_id, last_name, department_id, job_id \n" +
                "   FROM employees\n" +
                "   WHERE job_id = 'PU_CLERK' \n" +
                "      or job_id = 'SH_CLERK' \n" +
                "      or job_id = 'ST_CLERK';", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE VIEW clerk AS\n" +
                "   SELECT employee_id, last_name, department_id, job_id \n" +
                "   FROM employees\n" +
                "   WHERE job_id = 'PU_CLERK' \n" +
                "      or job_id = 'SH_CLERK' \n" +
                "      or job_id = 'ST_CLERK'\n" +
                "   WITH CHECK OPTION;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW clerk AS\n" +
                "   SELECT employee_id, last_name, department_id, job_id \n" +
                "   FROM employees\n" +
                "   WHERE job_id = 'PU_CLERK' \n" +
                "      or job_id = 'SH_CLERK' \n" +
                "      or job_id = 'ST_CLERK'\n" +
                "   WITH CHECK OPTION;", format);
    }
}
