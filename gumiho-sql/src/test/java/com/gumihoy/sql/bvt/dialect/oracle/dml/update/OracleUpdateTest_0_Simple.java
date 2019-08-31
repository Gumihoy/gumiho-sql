package com.gumihoy.sql.bvt.dialect.oracle.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleUpdateTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "UPDATE employees\n" +
                "   SET commission_pct = NULL\n" +
                "   WHERE job_id = 'SH_CLERK';";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE employees\n" +
                "SET commission_pct = NULL\n" +
                "WHERE job_id = 'SH_CLERK';", format);
    }


    @Test
    public void test_1() {
        String sql = "UPDATE employees SET \n" +
                "    job_id = 'SA_MAN', salary = salary + 1000, department_id = 120 \n" +
                "    WHERE first_name||' '||last_name = 'Douglas Grant'; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE employees\n" +
                "SET job_id = 'SA_MAN', salary = salary + 1000,\n" +
                "\tdepartment_id = 120\n" +
                "WHERE first_name || ' ' || last_name = 'Douglas Grant';", format);
    }

    @Test
    public void test_2() {
        String sql = "UPDATE employees@remote\n" +
                "   SET salary = salary*1.1\n" +
                "   WHERE last_name = 'Baer';";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE employees@remote\n" +
                "SET salary = salary * 1.1\n" +
                "WHERE last_name = 'Baer';", format);
    }

    @Test
    public void test_3() {
        String sql = "UPDATE employees a \n" +
                "    SET department_id = \n" +
                "        (SELECT department_id \n" +
                "            FROM departments \n" +
                "            WHERE location_id = '2100'), \n" +
                "        (salary, commission_pct) = \n" +
                "        (SELECT 1.1*AVG(salary), 1.5*AVG(commission_pct) \n" +
                "          FROM employees b \n" +
                "          WHERE a.department_id = b.department_id) \n" +
                "    WHERE department_id IN \n" +
                "        (SELECT department_id \n" +
                "          FROM departments\n" +
                "          WHERE location_id = 2900 \n" +
                "              OR location_id = 2700); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE employees a\n" +
                "SET department_id = (\n" +
                "\t\tSELECT department_id\n" +
                "\t\tFROM departments\n" +
                "\t\tWHERE location_id = '2100'\n" +
                "\t),\n" +
                "\t(salary, commission_pct) = (\n" +
                "\t\tSELECT 1.1 * AVG(salary), 1.5 * AVG(commission_pct)\n" +
                "\t\tFROM employees b\n" +
                "\t\tWHERE a.department_id = b.department_id\n" +
                "\t)\n" +
                "WHERE department_id IN (\n" +
                "\t\tSELECT department_id\n" +
                "\t\tFROM departments\n" +
                "\t\tWHERE location_id = 2900 OR location_id = 2700\n" +
                "\t);", format);
    }
}
