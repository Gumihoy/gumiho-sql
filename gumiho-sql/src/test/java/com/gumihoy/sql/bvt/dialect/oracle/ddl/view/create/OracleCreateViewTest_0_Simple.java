package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateViewTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE VIEW emp_view AS \n" +
                "   SELECT last_name, salary*12 annual_salary\n" +
                "   FROM employees \n" +
                "   WHERE department_id = 20;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW emp_view\n" +
                "AS\n" +
                "\tSELECT last_name, salary * 12 annual_salary\n" +
                "\tFROM employees\n" +
                "\tWHERE department_id = 20;", format);
    }

}
