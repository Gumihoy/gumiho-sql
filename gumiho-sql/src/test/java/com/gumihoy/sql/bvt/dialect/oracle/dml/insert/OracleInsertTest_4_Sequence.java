package com.gumihoy.sql.bvt.dialect.oracle.dml.insert;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-05.
 */
public class OracleInsertTest_4_Sequence {

    @Test
    public void test_0() {
        String sql = "INSERT INTO departments\n" +
                "   VALUES (280, 'Recreation', 121, 1700);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO departments\n" +
                "   VALUES (280, 'Recreation', 121, 1700);", format);
    }

    @Test
    public void test_1() {
        String sql = "INSERT INTO departments\n" +
                "   VALUES (280, 'Recreation', DEFAULT, 1700);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO departments\n" +
                "   VALUES (280, 'Recreation', DEFAULT, 1700);", format);
    }

    @Test
    public void test_2() {
        String sql = "INSERT INTO employees (employee_id, last_name, email, \n" +
                "      hire_date, job_id, salary, commission_pct) \n" +
                "   VALUES (207, 'Gregory', 'pgregory@example.com', \n" +
                "      sysdate, 'PU_CLERK', 1.2E3, NULL);\n";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO employees (employee_id, last_name, email, \n" +
                "      hire_date, job_id, salary, commission_pct) \n" +
                "   VALUES (207, 'Gregory', 'pgregory@example.com', \n" +
                "      sysdate, 'PU_CLERK', 1.2E3, NULL);\n", format);
    }


    @Test
    public void test_3() {
        String sql = "INSERT INTO employees (employee_id, last_name, email, \n" +
                "      hire_date, job_id, salary, commission_pct) \n" +
                "   VALUES (207, 'Gregory', 'pgregory@example.com', \n" +
                "      sysdate, 'PU_CLERK', 1.2E3, NULL);\n";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO employees (employee_id, last_name, email, \n" +
                "      hire_date, job_id, salary, commission_pct) \n" +
                "   VALUES (207, 'Gregory', 'pgregory@example.com', \n" +
                "      sysdate, 'PU_CLERK', 1.2E3, NULL);\n", format);
    }
}
