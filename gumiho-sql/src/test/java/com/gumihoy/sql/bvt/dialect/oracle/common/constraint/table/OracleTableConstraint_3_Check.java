package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.table;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleTableConstraint_3_Check {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE dept_20\n" +
                "   (employee_id     NUMBER(4) PRIMARY KEY, \n" +
                "    last_name       VARCHAR2(10), \n" +
                "    job_id          VARCHAR2(9), \n" +
                "    manager_id      NUMBER(4), \n" +
                "    salary          NUMBER(7,2), \n" +
                "    commission_pct  NUMBER(7,2), \n" +
                "    department_id   NUMBER(2),\n" +
                "    CONSTRAINT check_sal CHECK (salary * commission_pct <= 5000));";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE dept_20 (\n" +
                "\temployee_id NUMBER(4) PRIMARY KEY,\n" +
                "\tlast_name VARCHAR2(10),\n" +
                "\tjob_id VARCHAR2(9),\n" +
                "\tmanager_id NUMBER(4),\n" +
                "\tsalary NUMBER(7, 2),\n" +
                "\tcommission_pct NUMBER(7, 2),\n" +
                "\tdepartment_id NUMBER(2),\n" +
                "\tCONSTRAINT check_sal CHECK (salary * commission_pct <= 5000)\n" +
                ");", format);
    }


    @Test
    public void test_1() {
        String sql = "CREATE TABLE students (name person_name, age INTEGER,\n" +
                "                           CHECK (name.first_name IS NOT NULL AND\n" +
                "                                   name.last_name IS NOT NULL));";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE students (\n" +
                "\tname person_name,\n" +
                "\tage INTEGER,\n" +
                "\tCHECK (name.first_name IS NOT NULL AND name.last_name IS NOT NULL)\n" +
                ");", format);
    }

}
