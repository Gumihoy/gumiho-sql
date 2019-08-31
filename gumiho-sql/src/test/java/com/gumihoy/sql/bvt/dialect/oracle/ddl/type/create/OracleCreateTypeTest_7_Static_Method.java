package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTypeTest_7_Static_Method {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TYPE department_t AS OBJECT (\n" +
                "   deptno number(10),\n" +
                "   dname CHAR(30));\n" +
                "\n" +
                "CREATE OR REPLACE TYPE employee_t AS OBJECT(\n" +
                "   empid RAW(16),\n" +
                "   ename CHAR(31),\n" +
                "   dept REF department_t,\n" +
                "      STATIC function construct_emp\n" +
                "      (name VARCHAR2, dept REF department_t)\n" +
                "      RETURN employee_t\n" +
                ");";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TYPE department_t AS OBJECT (\n" +
                "   deptno number(10),\n" +
                "   dname CHAR(30));\n" +
                "\n" +
                "CREATE OR REPLACE TYPE employee_t AS OBJECT(\n" +
                "   empid RAW(16),\n" +
                "   ename CHAR(31),\n" +
                "   dept REF department_t,\n" +
                "      STATIC function construct_emp\n" +
                "      (name VARCHAR2, dept REF department_t)\n" +
                "      RETURN employee_t\n" +
                ");", format);
    }

}
