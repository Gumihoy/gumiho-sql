package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_8_DELETE_CASCADE {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER dept_del_cascade\n" +
                "  AFTER DELETE ON dept\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "  -- Before row is deleted from dept,\n" +
                "  -- delete all rows from emp table whose DEPTNO is same as\n" +
                "  -- DEPTNO being deleted from dept table:\n" +
                "\n" +
                "BEGIN\n" +
                "  DELETE FROM emp\n" +
                "  WHERE emp.Deptno = :OLD.Deptno;\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER dept_del_cascade\n" +
                "  AFTER DELETE ON dept\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "  -- Before row is deleted from dept,\n" +
                "  -- delete all rows from emp table whose DEPTNO is same as\n" +
                "  -- DEPTNO being deleted from dept table:\n" +
                "\n" +
                "BEGIN\n" +
                "  DELETE FROM emp\n" +
                "  WHERE emp.Deptno = :OLD.Deptno;\n" +
                "END;\n" +
                "/", format);
    }


}
