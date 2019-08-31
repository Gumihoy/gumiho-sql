package com.gumihoy.sql.bvt.dialect.oracle.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleUpdateTest_4_Returning {

    @Test
    public void test_0() {
        String sql = "UPDATE employees\n" +
                "  SET job_id ='SA_MAN', salary = salary + 1000, department_id = 140\n" +
                "  WHERE last_name = 'Jones'\n" +
                "  RETURNING salary*0.25, last_name, department_id\n" +
                "    INTO :bnd1, :bnd2, :bnd3;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE employees\n" +
                "SET job_id = 'SA_MAN', salary = salary + 1000,\n" +
                "\tdepartment_id = 140\n" +
                "WHERE last_name = 'Jones'\n" +
                "RETURNING salary * 0.25, last_name, department_id INTO :bnd1, :bnd2, :bnd3;", format);
    }


    @Test
    public void test_1() {
        String sql = "UPDATE employees\n" +
                "   SET salary = salary * 1.1\n" +
                "   WHERE department_id = 100\n" +
                "   RETURNING SUM(salary) INTO :bnd1;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE employees\n" +
                "SET salary = salary * 1.1\n" +
                "WHERE department_id = 100\n" +
                "RETURNING SUM(salary) INTO :bnd1;", format);
    }
}
