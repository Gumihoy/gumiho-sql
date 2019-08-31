package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateViewTest_2_Constraints {

    @Test
    public void test_0() {
        String sql = "CREATE VIEW emp_sal (emp_id, last_name, \n" +
                "      email UNIQUE RELY DISABLE NOVALIDATE,\n" +
                "   CONSTRAINT id_pk PRIMARY KEY (emp_id) RELY DISABLE NOVALIDATE)\n" +
                "   AS SELECT employee_id, last_name, email FROM employees;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW emp_sal (emp_id, last_name, \n" +
                "      email UNIQUE RELY DISABLE NOVALIDATE,\n" +
                "   CONSTRAINT id_pk PRIMARY KEY (emp_id) RELY DISABLE NOVALIDATE)\n" +
                "   AS SELECT employee_id, last_name, email FROM employees;", format);
    }

}
