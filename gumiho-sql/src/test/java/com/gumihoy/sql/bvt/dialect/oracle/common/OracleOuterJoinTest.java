package com.gumihoy.sql.bvt.dialect.oracle.common;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleOuterJoinTest {

    @Test
    public void test_0() {
        String sql = "SELECT employee_id, manager_id \n" +
                "   FROM employees\n" +
                "   WHERE employees.manager_id(+) = employees.employee_id;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT employee_id, manager_id\n" +
                "FROM employees\n" +
                "WHERE employees.manager_id(+) = employees.employee_id;", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT e1.employee_id, e1.manager_id, e2.employee_id\n" +
                "   FROM employees e1, employees e2\n" +
                "   WHERE e1.manager_id(+) = e2.employee_id\n" +
                "   ORDER BY e1.employee_id, e1.manager_id, e2.employee_id;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT e1.employee_id, e1.manager_id, e2.employee_id\n" +
                "FROM employees e1, employees e2\n" +
                "WHERE e1.manager_id(+) = e2.employee_id\n" +
                "ORDER BY e1.employee_id, e1.manager_id, e2.employee_id;", format);
    }


    @Test
    public void test_2() {
        String sql = "SELECT * FROM A, B, D\n" +
                "  WHERE A.c1 = B.c2(+) and D.c3 = B.c4(+);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM A, B, D\n" +
                "WHERE A.c1 = B.c2(+) AND D.c3 = B.c4(+);", format);
    }

}
