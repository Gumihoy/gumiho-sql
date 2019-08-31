package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.table;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleTableConstraint_2_Foreign_Key {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE dept_20 \n" +
                "   (employee_id     NUMBER(4), \n" +
                "    last_name       VARCHAR2(10), \n" +
                "    job_id          VARCHAR2(9), \n" +
                "    manager_id      NUMBER(4), \n" +
                "    hire_date       DATE, \n" +
                "    salary          NUMBER(7,2), \n" +
                "    commission_pct  NUMBER(7,2), \n" +
                "    department_id, \n" +
                "   CONSTRAINT fk_deptno \n" +
                "      FOREIGN  KEY (department_id) \n" +
                "      REFERENCES  departments(department_id) ); ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE dept_20 (\n" +
                "\temployee_id NUMBER(4),\n" +
                "\tlast_name VARCHAR2(10),\n" +
                "\tjob_id VARCHAR2(9),\n" +
                "\tmanager_id NUMBER(4),\n" +
                "\thire_date DATE,\n" +
                "\tsalary NUMBER(7, 2),\n" +
                "\tcommission_pct NUMBER(7, 2),\n" +
                "\tdepartment_id ,\n" +
                "\tCONSTRAINT fk_deptno FOREIGN KEY (department_id) REFERENCES departments(department_id)\n" +
                ");", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE dept_20\n" +
                "   ADD CONSTRAINT fk_empid_hiredate\n" +
                "   FOREIGN KEY (employee_id, hire_date)\n" +
                "   REFERENCES hr.job_history(employee_id, start_date)\n" +
                "   EXCEPTIONS INTO wrong_emp;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE dept_20\n" +
                "   ADD CONSTRAINT fk_empid_hiredate\n" +
                "   FOREIGN KEY (employee_id, hire_date)\n" +
                "   REFERENCES hr.job_history(employee_id, start_date)\n" +
                "   EXCEPTIONS INTO wrong_emp;", format);
    }
}
