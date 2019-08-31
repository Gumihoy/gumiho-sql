package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_7_Parallelism {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE dept_80\n" +
                "   PARALLEL\n" +
                "   AS SELECT * FROM employees\n" +
                "   WHERE department_id = 80;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE dept_80\n" +
                "   PARALLEL\n" +
                "   AS SELECT * FROM employees\n" +
                "   WHERE department_id = 80;", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE TABLE dept_80\n" +
                "   AS SELECT * FROM employees\n" +
                "   WHERE department_id = 80;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE dept_80\n" +
                "   AS SELECT * FROM employees\n" +
                "   WHERE department_id = 80;", format);
    }

}
