package com.gumihoy.sql.bvt.dialect.oracle.condition;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleBetweenConditionTest {

    @Test
    public void test_0() {
        String sql = "SELECT * FROM employees\n" +
                "  WHERE salary\n" +
                "  BETWEEN 2000 AND 3000\n" +
                "  ORDER BY employee_id;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM employees\n" +
                "WHERE salary BETWEEN 2000 AND 3000\n" +
                "ORDER BY employee_id;", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT * FROM employees\n" +
                "  WHERE salary\n" +
                "  NOT BETWEEN 2000 AND 3000\n" +
                "  ORDER BY employee_id;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM employees\n" +
                "WHERE salary NOT BETWEEN 2000 AND 3000\n" +
                "ORDER BY employee_id;", format);
    }

}
