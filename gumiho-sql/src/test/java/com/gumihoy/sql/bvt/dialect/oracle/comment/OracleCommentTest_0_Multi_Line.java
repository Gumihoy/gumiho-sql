package com.gumihoy.sql.bvt.dialect.oracle.comment;

import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCommentTest_0_Multi_Line {

    @Test
    public void test_0() {
        String sql = "SELECT last_name, employee_id, salary + NVL(commission_pct, 0), \n" +
                "       job_id, e.department_id\n" +
                "  /* Select all employees whose compensation is\n" +
                "  greater than that of Pataballa.*/\n" +
                "  FROM employees e, departments d\n" +
                "  /*The DEPARTMENTS table is used to get the department name.*/\n" +
                "  WHERE e.department_id = d.department_id\n" +
                "    AND salary + NVL(commission_pct,0) >   /* Subquery:       */\n" +
                "      (SELECT salary + NVL(commission_pct,0)\n" +
                "        /* total compensation is salary + commission_pct */\n" +
                "        FROM employees \n" +
                "        WHERE last_name = 'Pataballa')\n" +
                "  ORDER BY last_name, employee_id;";
        System.out.println(sql);
        SQLOutputConfig config = new SQLOutputConfig();
        config.keepComment = true;
        String format = SQLUtils.format(sql, DBType.Oracle, config);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT last_name, employee_id, salary + NVL(commission_pct, 0), \n" +
                "       job_id, e.department_id\n" +
                "  /* Select all employees whose compensation is\n" +
                "  greater than that of Pataballa.*/\n" +
                "  FROM employees e, departments d\n" +
                "  /*The DEPARTMENTS table is used to get the department name.*/\n" +
                "  WHERE e.department_id = d.department_id\n" +
                "    AND salary + NVL(commission_pct,0) >   /* Subquery:       */\n" +
                "      (SELECT salary + NVL(commission_pct,0)\n" +
                "        /* total compensation is salary + commission_pct */\n" +
                "        FROM employees \n" +
                "        WHERE last_name = 'Pataballa')\n" +
                "  ORDER BY last_name, employee_id;", format);
    }


}
