package com.gumihoy.sql.bvt.dialect.oracle.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleSelectTest_12_WITH_CHECK_OPTION {

    @Test
    public void test_0() {
        String sql = "SELECT e.employee_id, e.salary, e.commission_pct\n" +
                "   FROM employees e, departments d\n" +
                "   WHERE job_id = 'SA_REP'\n" +
                "   AND e.department_id = d.department_id\n" +
                "   AND location_id = 2500\n" +
                "   ORDER BY e.employee_id\n" +
                "   FOR UPDATE;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT e.employee_id, e.salary, e.commission_pct\n" +
                "   FROM employees e, departments d\n" +
                "   WHERE job_id = 'SA_REP'\n" +
                "   AND e.department_id = d.department_id\n" +
                "   AND location_id = 2500\n" +
                "   ORDER BY e.employee_id\n" +
                "   FOR UPDATE;", format);
    }


    @Test
    public void test_1() {
        String sql = "SELECT e.employee_id, e.salary, e.commission_pct\n" +
                "   FROM employees e JOIN departments d\n" +
                "   USING (department_id)\n" +
                "   WHERE job_id = 'SA_REP'\n" +
                "   AND location_id = 2500\n" +
                "   ORDER BY e.employee_id\n" +
                "   FOR UPDATE OF e.salary;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT e.employee_id, e.salary, e.commission_pct\n" +
                "   FROM employees e JOIN departments d\n" +
                "   USING (department_id)\n" +
                "   WHERE job_id = 'SA_REP'\n" +
                "   AND location_id = 2500\n" +
                "   ORDER BY e.employee_id\n" +
                "   FOR UPDATE OF e.salary;", format);
    }


}
