package com.gumihoy.sql.bvt.dialect.oracle.common;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleHierarchicalQueriesTest {

    @Test
    public void test_0() {
        String sql = "SELECT employee_id, last_name, manager_id\n" +
                "   FROM employees\n" +
                "   CONNECT BY PRIOR employee_id = manager_id;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT employee_id, last_name, manager_id\n" +
                "   FROM employees\n" +
                "   CONNECT BY PRIOR employee_id = manager_id;", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT employee_id, last_name, manager_id, LEVEL\n" +
                "   FROM employees\n" +
                "   CONNECT BY PRIOR employee_id = manager_id;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT employee_id, last_name, manager_id, LEVEL\n" +
                "   FROM employees\n" +
                "   CONNECT BY PRIOR employee_id = manager_id;", format);
    }


    @Test
    public void test_2() {
        String sql = "SELECT last_name, employee_id, manager_id, LEVEL\n" +
                "      FROM employees\n" +
                "      START WITH employee_id = 100\n" +
                "      CONNECT BY PRIOR employee_id = manager_id\n" +
                "      ORDER SIBLINGS BY last_name;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT last_name, employee_id, manager_id, LEVEL\n" +
                "      FROM employees\n" +
                "      START WITH employee_id = 100\n" +
                "      CONNECT BY PRIOR employee_id = manager_id\n" +
                "      ORDER SIBLINGS BY last_name;", format);
    }

    @Test
    public void test_3() {
        String sql = "SELECT last_name \"Employee\", CONNECT_BY_ISCYCLE \"Cycle\",\n" +
                "   LEVEL, SYS_CONNECT_BY_PATH(last_name, '/') \"Path\"\n" +
                "   FROM employees\n" +
                "   WHERE level <= 3 AND department_id = 80\n" +
                "   START WITH last_name = 'King'\n" +
                "   CONNECT BY NOCYCLE PRIOR employee_id = manager_id AND LEVEL <= 4\n" +
                "   ORDER BY \"Employee\", \"Cycle\", LEVEL, \"Path\";";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT last_name \"Employee\", CONNECT_BY_ISCYCLE \"Cycle\", LEVEL,\n" +
                "\tSYS_CONNECT_BY_PATH(last_name, '/') \"Path\"\n" +
                "FROM employees\n" +
                "WHERE level <= 3 AND department_id = 80\n" +
                "START WITH last_name = 'King'\n" +
                "CONNECT BY NOCYCLE PRIOR employee_id = manager_id AND LEVEL <= 4\n" +
                "ORDER BY \"Employee\", \"Cycle\", LEVEL, \"Path\";", format);
    }
}
