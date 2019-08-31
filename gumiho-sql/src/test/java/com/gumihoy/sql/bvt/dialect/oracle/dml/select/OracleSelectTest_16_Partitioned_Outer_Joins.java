package com.gumihoy.sql.bvt.dialect.oracle.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleSelectTest_16_Partitioned_Outer_Joins {

    @Test
    public void test_0() {
        String sql = "SELECT last_name, department_id FROM employees\n" +
                "   WHERE department_id =\n" +
                "     (SELECT department_id FROM employees\n" +
                "      WHERE last_name = 'Lorentz')\n" +
                "   ORDER BY last_name, department_id; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT last_name, department_id FROM employees\n" +
                "   WHERE department_id =\n" +
                "     (SELECT department_id FROM employees\n" +
                "      WHERE last_name = 'Lorentz')\n" +
                "   ORDER BY last_name, department_id; ", format);
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
