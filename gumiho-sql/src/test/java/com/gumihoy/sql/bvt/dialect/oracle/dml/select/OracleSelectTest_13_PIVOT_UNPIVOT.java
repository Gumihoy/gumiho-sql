package com.gumihoy.sql.bvt.dialect.oracle.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleSelectTest_13_PIVOT_UNPIVOT {

    @Test
    public void test_0() {
        String sql = "SELECT last_name, job_id, departments.department_id, department_name\n" +
                "   FROM employees, departments\n" +
                "   WHERE employees.department_id = departments.department_id\n" +
                "   ORDER BY last_name, job_id;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT last_name, job_id, departments.department_id, department_name\n" +
                "   FROM employees, departments\n" +
                "   WHERE employees.department_id = departments.department_id\n" +
                "   ORDER BY last_name, job_id;", format);
    }


    @Test
    public void test_1() {
        String sql = "SELECT * FROM pivot_table\n" +
                "  UNPIVOT (yearly_total FOR order_mode IN (store AS 'direct',\n" +
                "           internet AS 'online'))\n" +
                "  ORDER BY year, order_mode;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT * FROM pivot_table\n" +
                "  UNPIVOT (yearly_total FOR order_mode IN (store AS 'direct',\n" +
                "           internet AS 'online'))\n" +
                "  ORDER BY year, order_mode;", format);
    }


}
