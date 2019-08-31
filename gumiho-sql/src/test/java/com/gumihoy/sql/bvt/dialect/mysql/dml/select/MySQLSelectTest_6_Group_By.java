package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class MySQLSelectTest_6_Group_By {

    @Test
    public void test_0() {
        String sql = "SELECT department_id, MIN(salary), MAX (salary)\n" +
                "     FROM employees\n" +
                "     GROUP BY department_id\n" +
                "   ORDER BY department_id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT department_id, MIN(salary), MAX(salary)\n" +
                "FROM employees\n" +
                "GROUP BY department_id\n" +
                "ORDER BY department_id;", format);
    }


    @Test
    public void test_1() {
        String sql = "SELECT DECODE(GROUPING(department_name), 1, 'All Departments',\n" +
                "      department_name) AS department_name,\n" +
                "   DECODE(GROUPING(job_id), 1, 'All Jobs', job_id) AS job_id,\n" +
                "   COUNT(*) \"Total Empl\", AVG(salary) * 12 \"Average Sal\"\n" +
                "   FROM employees e, departments d\n" +
                "   WHERE d.department_id = e.department_id\n" +
                "   GROUP BY CUBE (department_name, job_id)\n" +
                "   ORDER BY department_name, job_id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT DECODE(GROUPING(department_name), 1, 'All Departments', department_name) AS department_name,\n" +
                "\tDECODE(GROUPING(job_id), 1, 'All Jobs', job_id) AS job_id,\n" +
                "\tCOUNT(*) 'Total Empl', AVG(salary) * 12 'Average Sal'\n" +
                "FROM employees e, departments d\n" +
                "WHERE d.department_id = e.department_id\n" +
                "GROUP BY CUBE(department_name, job_id)\n" +
                "ORDER BY department_name, job_id;", format);
    }


    @Test
    public void test_2() {
        String sql = "SELECT department_id, MIN(salary), MAX (salary)\n" +
                "   FROM employees\n" +
                "   GROUP BY department_id\n" +
                "   HAVING MIN(salary) < 5000\n" +
                "   ORDER BY department_id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT department_id, MIN(salary), MAX(salary)\n" +
                "FROM employees\n" +
                "GROUP BY department_id\n" +
                "HAVING MIN(salary) < 5000\n" +
                "ORDER BY department_id;", format);
    }


    @Test
    public void test_3() {
        String sql = "SELECT department_id, manager_id \n" +
                "   FROM employees \n" +
                "   GROUP BY department_id, manager_id HAVING (department_id, manager_id) IN\n" +
                "   (SELECT department_id, manager_id FROM employees x \n" +
                "      WHERE x.department_id = employees.department_id)\n" +
                "   ORDER BY department_id;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT department_id, manager_id\n" +
                "FROM employees\n" +
                "GROUP BY department_id, manager_id\n" +
                "HAVING (department_id, manager_id) IN (\n" +
                "\tSELECT department_id, manager_id\n" +
                "\tFROM employees x\n" +
                "\tWHERE x.department_id = employees.department_id\n" +
                ")\n" +
                "ORDER BY department_id;", format);
    }
}
