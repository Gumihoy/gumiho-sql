package com.gumihoy.sql.bvt.dialect.oracle.condition;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleLikeConditionTest {

    @Test
    public void test_0() {
        String sql = "SELECT salary \n" +
                "    FROM employees\n" +
                "    WHERE last_name LIKE 'R%'\n" +
                "    ORDER BY salary;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT salary\n" +
                "FROM employees\n" +
                "WHERE last_name LIKE 'R%'\n" +
                "ORDER BY salary;", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT last_name \n" +
                "    FROM employees\n" +
                "    WHERE last_name LIKE '%A\\_B%' ESCAPE '\\'\n" +
                "    ORDER BY last_name;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT last_name \n" +
                "    FROM employees\n" +
                "    WHERE last_name LIKE '%A\\_B%' ESCAPE '\\'\n" +
                "    ORDER BY last_name;", format);
    }

}
