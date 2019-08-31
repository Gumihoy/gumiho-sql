package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class MySQLSelectTest_4_Partition {

    @Test
    public void test_0() {
        String sql = "SELECT * FROM employees as e PARTITION (p0, p2)\n" +
                "  WHERE lname LIKE 'S%';";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM employees AS e\n" +
                "PARTITION (p0, p2)\n" +
                "WHERE lname LIKE 'S%';", format);
    }


    @Test
    public void test_1() {
        String sql = "SELECT  e.id AS 'Employee ID', CONCAT(e.fname, ' ', e.lname) AS Name,\n" +
                "    s.city AS City, d.name AS department\n" +
                "     FROM employees AS e\n" +
                "         JOIN stores AS s   ON e.store_id=s.id\n" +
                "         JOIN departments AS d  ON e.department_id=d.id\n" +
                "         PARTITION (p0, p2)\n" +
                "     ORDER BY e.lname;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT e.id AS 'Employee ID', CONCAT(e.fname, ' ', e.lname) AS Name,\n" +
                "\ts.city AS City, d.name AS department\n" +
                "FROM employees AS e\n" +
                "\tJOIN stores AS s ON e.store_id = s.id\n" +
                "\tJOIN departments AS d ON e.department_id = d.id\n" +
                "PARTITION (p0, p2)\n" +
                "ORDER BY e.lname;", format);
    }
}
