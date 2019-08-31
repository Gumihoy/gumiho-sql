package com.gumihoy.sql.bvt.dialect.oracle.condition;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleInConditionTest {

    @Test
    public void test_0() {
        String sql = "SELECT 'True' FROM employees\n" +
                "   WHERE department_id NOT IN (10, 20);\n";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'True'\n" +
                "FROM employees\n" +
                "WHERE department_id NOT IN (10, 20);", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT 'True' FROM employees\n" +
                "    WHERE department_id NOT IN (10, 20, NULL); ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'True'\n" +
                "FROM employees\n" +
                "WHERE department_id NOT IN (10, 20, NULL);", format);
    }

    @Test
    public void test_2() {
        String sql = "SELECT 'True' FROM employees\n" +
                "   WHERE department_id NOT IN (SELECT 0 FROM DUAL WHERE 1=2);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT 'True'\n" +
                "FROM employees\n" +
                "WHERE department_id NOT IN (\n" +
                "\t\tSELECT 0\n" +
                "\t\tFROM DUAL\n" +
                "\t\tWHERE 1 = 2\n" +
                "\t);", format);
    }


//    @Test
//    public void test_3() {
//        String sql = "SELECT v.employee_id, v.last_name, v.lev FROM\n" +
//                "      (SELECT employee_id, last_name, LEVEL lev \n" +
//                "      FROM employees v\n" +
//                "      START WITH employee_id = 100 \n" +
//                "      CONNECT BY PRIOR employee_id = manager_id) v \n" +
//                "   WHERE (v.employee_id, v.lev) IN\n" +
//                "      (SELECT employee_id, 2 FROM employees); ";
//        String format = SQLUtils.format(sql, DBType.Oracle);
//        System.out.println(sql);
//        System.out.println("----------------");
//        System.out.println(format);
//        Assert.assertEquals("SELECT v.employee_id, v.last_name, v.lev FROM\n" +
//                "      (SELECT employee_id, last_name, LEVEL lev \n" +
//                "      FROM employees v\n" +
//                "      START WITH employee_id = 100 \n" +
//                "      CONNECT BY PRIOR employee_id = manager_id) v \n" +
//                "   WHERE (v.employee_id, v.lev) IN\n" +
//                "      (SELECT employee_id, 2 FROM employees); ", format);
//    }
}
