package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTypeTest_2_Hierarchy {

    @Test
    public void test_0() {
        String sql = "CREATE TYPE person_t AS OBJECT (name VARCHAR2(100), ssn NUMBER) \n" +
                "   NOT FINAL;\n" +
                "\n" +
                "CREATE TYPE employee_t UNDER person_t \n" +
                "   (department_id NUMBER, salary NUMBER) NOT FINAL;\n" +
                "\n" +
                "CREATE TYPE part_time_emp_t UNDER employee_t (num_hrs NUMBER);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TYPE person_t AS OBJECT (name VARCHAR2(100), ssn NUMBER) \n" +
                "   NOT FINAL;\n" +
                "\n" +
                "CREATE TYPE employee_t UNDER person_t \n" +
                "   (department_id NUMBER, salary NUMBER) NOT FINAL;\n" +
                "\n" +
                "CREATE TYPE part_time_emp_t UNDER employee_t (num_hrs NUMBER);", format);
    }


}
