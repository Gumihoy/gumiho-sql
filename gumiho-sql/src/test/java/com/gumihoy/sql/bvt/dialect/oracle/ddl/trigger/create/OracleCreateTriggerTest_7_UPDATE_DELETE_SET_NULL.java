package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_7_UPDATE_DELETE_SET_NULL {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER dept_set_null\n" +
                "  AFTER DELETE OR UPDATE OF Deptno ON dept\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "  -- Before row is deleted from dept or primary key (DEPTNO) of dept is updated,\n" +
                "  -- set all corresponding dependent foreign key values in emp to NULL:\n" +
                "\n" +
                "BEGIN\n" +
                "  IF UPDATING AND :OLD.Deptno != :NEW.Deptno OR DELETING THEN\n" +
                "    UPDATE emp SET emp.Deptno = NULL\n" +
                "    WHERE emp.Deptno = :OLD.Deptno;\n" +
                "  END IF;\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER dept_set_null\n" +
                "\tAFTER\n" +
                "\t\tDELETE OR\n" +
                "\t\tUPDATE OF Deptno\n" +
                "\tON dept\n" +
                "\tFOR EACH ROW\n" +
                "\tBEGIN\n" +
                "\t\tIF UPDATING AND :OLD.Deptno != :NEW.Deptno OR DELETING THEN\n" +
                "\t\t\tUPDATE emp\n" +
                "\t\t\tSET emp.Deptno = NULL\n" +
                "\t\t\tWHERE emp.Deptno = :OLD.Deptno;\n" +
                "\t\tEND IF;\n" +
                "\tEND;", format);
    }


}
