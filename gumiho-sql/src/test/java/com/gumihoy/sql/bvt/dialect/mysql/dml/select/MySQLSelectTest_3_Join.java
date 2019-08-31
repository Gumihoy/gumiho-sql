package com.gumihoy.sql.bvt.dialect.mysql.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-15.
 */
public class MySQLSelectTest_3_Join {

    @Test
    public void test_0() {
        String sql = "SELECT * FROM t1 LEFT JOIN (t2, t3, t4)\n" +
                "                 ON (t2.a = t1.a AND t3.b = t1.b AND t4.c = t1.c);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tLEFT JOIN (t2, t3, t4) ON (t2.a = t1.a AND t3.b = t1.b AND t4.c = t1.c);", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT * FROM t1 LEFT JOIN (t2 CROSS JOIN t3 CROSS JOIN t4)\n" +
                "                 ON (t2.a = t1.a AND t3.b = t1.b AND t4.c = t1.c);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tLEFT JOIN (t2\n" +
                "\t\tCROSS JOIN t3\n" +
                "\t\tCROSS JOIN t4) ON (t2.a = t1.a AND t3.b = t1.b AND t4.c = t1.c);", format);
    }

    @Test
    public void test_2() {
        String sql = "SELECT t1.name, t2.salary\n" +
                "  FROM employee AS t1 INNER JOIN info AS t2 ON t1.name = t2.name;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT t1.name, t2.salary\n" +
                "FROM employee AS t1\n" +
                "\tINNER JOIN info AS t2 ON t1.name = t2.name;", format);
    }

    @Test
    public void test_3() {
        String sql = "SELECT t1.name, t2.salary\n" +
                "  FROM employee t1 INNER JOIN info t2 ON t1.name = t2.name;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT t1.name, t2.salary\n" +
                "FROM employee t1\n" +
                "\tINNER JOIN info t2 ON t1.name = t2.name;", format);
    }

    @Test
    public void test_4() {
        String sql = "SELECT * FROM (SELECT 1, 2, 3) AS t1;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM (\n" +
                "\tSELECT 1, 2, 3\n" +
                ") AS t1;", format);
    }

    @Test
    public void test_5() {
        String sql = "SELECT left_tbl.*\n" +
                "  FROM left_tbl LEFT JOIN right_tbl ON left_tbl.id = right_tbl.id\n" +
                "  WHERE right_tbl.id IS NULL;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT left_tbl.*\n" +
                "FROM left_tbl\n" +
                "\tLEFT JOIN right_tbl ON left_tbl.id = right_tbl.id\n" +
                "WHERE right_tbl.id IS NULL;", format);
    }

    @Test
    public void test_6() {
        String sql = "SELECT left_tbl.*\n" +
                "    FROM { OJ left_tbl LEFT OUTER JOIN right_tbl\n" +
                "           ON left_tbl.id = right_tbl.id }\n" +
                "    WHERE right_tbl.id IS NULL;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT left_tbl.*\n" +
                "FROM { OJ left_tbl\n" +
                "\tLEFT OUTER JOIN right_tbl ON left_tbl.id = right_tbl.id }\n" +
                "WHERE right_tbl.id IS NULL;", format);
    }

    @Test
    public void test_7() {
        String sql = "SELECT * FROM table1, table2;\n" +
                "SELECT * FROM table1 INNER JOIN table2 ON table1.id = table2.id;\n" +
                "SELECT * FROM table1 LEFT JOIN table2 ON table1.id = table2.id;\n" +
                "SELECT * FROM table1 LEFT JOIN table2 USING (id);\n" +
                "SELECT * FROM table1 LEFT JOIN table2 ON table1.id = table2.id\n" +
                "  LEFT JOIN table3 ON table2.id = table3.id;\n";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM table1, table2;\n" +
                "SELECT *\n" +
                "FROM table1\n" +
                "\tINNER JOIN table2 ON table1.id = table2.id;\n" +
                "SELECT *\n" +
                "FROM table1\n" +
                "\tLEFT JOIN table2 ON table1.id = table2.id;\n" +
                "SELECT *\n" +
                "FROM table1\n" +
                "\tLEFT JOIN table2 USING (id);\n" +
                "SELECT *\n" +
                "FROM table1\n" +
                "\tLEFT JOIN table2 ON table1.id = table2.id\n" +
                "\tLEFT JOIN table3 ON table2.id = table3.id;", format);
    }


    @Test
    public void test_8() {
        String sql = "SELECT * FROM t1 NATURAL JOIN t2;\n" +
                "SELECT * FROM t1 JOIN t2 USING (j);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tNATURAL JOIN t2;\n" +
                "SELECT *\n" +
                "FROM t1\n" +
                "\tJOIN t2 USING (j);", format);
    }

    @Test
    public void test_9() {
        String sql = "SELECT * FROM t1 NATURAL LEFT JOIN t2;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tNATURAL LEFT JOIN t2;", format);
    }

    @Test
    public void test_10() {
        String sql = "SELECT * FROM t1 NATURAL RIGHT JOIN t2;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tNATURAL RIGHT JOIN t2;", format);
    }

    @Test
    public void test_11() {
        String sql = "SELECT * FROM t1 LEFT JOIN t2 ON (t1.a = t2.a);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tLEFT JOIN t2 ON (t1.a = t2.a);", format);
    }

    @Test
    public void test_12() {
        String sql = "SELECT * FROM t1 RIGHT JOIN t2 ON (t1.a = t2.a);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tRIGHT JOIN t2 ON (t1.a = t2.a);", format);
    }

    @Test
    public void test_13() {
        String sql = "SELECT * FROM t1 JOIN t2 ON (i1 = i3) JOIN t3;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tJOIN t2 ON (i1 = i3)\n" +
                "\tJOIN t3;", format);
    }

    @Test
    public void test_14() {
        String sql = "SELECT * FROM t1 JOIN t2 JOIN t3 ON (i1 = i3);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tJOIN t2\n" +
                "\tJOIN t3 ON (i1 = i3);", format);
    }

    @Test
    public void test_15() {
        String sql = "SELECT * FROM t1, t2 JOIN t3 ON (t1.i1 = t3.i3);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1, t2\n" +
                "\tJOIN t3 ON (t1.i1 = t3.i3);", format);
    }

    @Test
    public void test_16() {
        String sql = "SELECT * FROM (t1, t2) JOIN t3 ON (t1.i1 = t3.i3);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM (t1, t2)\n" +
                "\tJOIN t3 ON (t1.i1 = t3.i3);", format);
    }

    @Test
    public void test_17() {
        String sql = "SELECT * FROM t1 JOIN t2 JOIN t3 ON (t1.i1 = t3.i3);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM t1\n" +
                "\tJOIN t2\n" +
                "\tJOIN t3 ON (t1.i1 = t3.i3);", format);
    }

    @Test
    public void test_18() {
        String sql = "SELECT * FROM employees PARTITION (p0, p2)\n" +
                "  WHERE lname LIKE 'S%';";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT *\n" +
                "FROM employees PARTITION (p0, p2)\n" +
                "WHERE lname LIKE 'S%';", format);
    }


    @Test
    public void test_19() {
        String sql = "SELECT  e.id AS 'Employee ID', CONCAT(e.fname, ' ', e.lname) AS Name,\n" +
                "    s.city AS City, d.name AS department\n" +
                "     FROM employees AS e\n" +
                "         JOIN stores PARTITION (p1) AS s ON e.store_id=s.id\n" +
                "         JOIN departments PARTITION (p0) AS d ON e.department_id=d.id\n" +
                "     ORDER BY e.lname;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT e.id AS 'Employee ID', CONCAT(e.fname, ' ', e.lname) AS Name,\n" +
                "\ts.city AS City, d.name AS department\n" +
                "FROM employees AS e\n" +
                "\tJOIN stores PARTITION (p1) AS s ON e.store_id = s.id\n" +
                "\tJOIN departments PARTITION (p0) AS d ON e.department_id = d.id\n" +
                "ORDER BY e.lname;", format);
    }


}
