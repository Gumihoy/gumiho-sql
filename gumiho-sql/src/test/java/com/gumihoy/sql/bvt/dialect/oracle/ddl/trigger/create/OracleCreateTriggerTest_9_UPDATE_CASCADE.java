package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_9_UPDATE_CASCADE {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER dept_cascade1\n" +
                "  BEFORE UPDATE OF Deptno ON dept\n" +
                "DECLARE\n" +
                "  -- Before updating dept table (this is a statement trigger),\n" +
                "  -- generate sequence number\n" +
                "  -- & assign it to public variable UPDATESEQ of\n" +
                "  -- user-defined package named INTEGRITYPACKAGE:\n" +
                "BEGIN\n" +
                "  Integritypackage.Updateseq := Update_sequence.NEXTVAL;\n" +
                "END;\n" +
                "/\n" +
                "CREATE OR REPLACE TRIGGER dept_cascade2\n" +
                "  AFTER DELETE OR UPDATE OF Deptno ON dept\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "  -- For each department number in dept that is updated,\n" +
                "  -- cascade update to dependent foreign keys in emp table.\n" +
                "  -- Cascade update only if child row was not updated by this trigger:\n" +
                "BEGIN\n" +
                "  IF UPDATING THEN\n" +
                "    UPDATE emp\n" +
                "    SET Deptno = :NEW.Deptno,\n" +
                "        Update_id = Integritypackage.Updateseq   --from 1st\n" +
                "    WHERE emp.Deptno = :OLD.Deptno\n" +
                "    AND Update_id IS NULL;\n" +
                "\n" +
                "    /* Only NULL if not updated by 3rd trigger\n" +
                "       fired by same triggering statement */\n" +
                "  END IF;\n" +
                "  IF DELETING THEN\n" +
                "    -- After row is deleted from dept,\n" +
                "    -- delete all rows from emp table whose DEPTNO is same as\n" +
                "    -- DEPTNO being deleted from dept table:\n" +
                "    DELETE FROM emp\n" +
                "    WHERE emp.Deptno = :OLD.Deptno;\n" +
                "  END IF;\n" +
                "END;\n" +
                "/\n" +
                "CREATE OR REPLACE TRIGGER dept_cascade3\n" +
                "  AFTER UPDATE OF Deptno ON dept\n" +
                "BEGIN UPDATE emp\n" +
                "  SET Update_id = NULL\n" +
                "  WHERE Update_id = Integritypackage.Updateseq;\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        SQLOutputConfig config = new SQLOutputConfig();
        config.printAfterSemi = false;
        String format = SQLUtils.format(sql, DBType.Oracle, config);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER dept_cascade1\n" +
                "  BEFORE UPDATE OF Deptno ON dept\n" +
                "DECLARE\n" +
                "  -- Before updating dept table (this is a statement trigger),\n" +
                "  -- generate sequence number\n" +
                "  -- & assign it to public variable UPDATESEQ of\n" +
                "  -- user-defined package named INTEGRITYPACKAGE:\n" +
                "BEGIN\n" +
                "  Integritypackage.Updateseq := Update_sequence.NEXTVAL;\n" +
                "END;\n" +
                "/\n" +
                "CREATE OR REPLACE TRIGGER dept_cascade2\n" +
                "  AFTER DELETE OR UPDATE OF Deptno ON dept\n" +
                "  FOR EACH ROW\n" +
                "\n" +
                "  -- For each department number in dept that is updated,\n" +
                "  -- cascade update to dependent foreign keys in emp table.\n" +
                "  -- Cascade update only if child row was not updated by this trigger:\n" +
                "BEGIN\n" +
                "  IF UPDATING THEN\n" +
                "    UPDATE emp\n" +
                "    SET Deptno = :NEW.Deptno,\n" +
                "        Update_id = Integritypackage.Updateseq   --from 1st\n" +
                "    WHERE emp.Deptno = :OLD.Deptno\n" +
                "    AND Update_id IS NULL;\n" +
                "\n" +
                "    /* Only NULL if not updated by 3rd trigger\n" +
                "       fired by same triggering statement */\n" +
                "  END IF;\n" +
                "  IF DELETING THEN\n" +
                "    -- After row is deleted from dept,\n" +
                "    -- delete all rows from emp table whose DEPTNO is same as\n" +
                "    -- DEPTNO being deleted from dept table:\n" +
                "    DELETE FROM emp\n" +
                "    WHERE emp.Deptno = :OLD.Deptno;\n" +
                "  END IF;\n" +
                "END;\n" +
                "/\n" +
                "CREATE OR REPLACE TRIGGER dept_cascade3\n" +
                "  AFTER UPDATE OF Deptno ON dept\n" +
                "BEGIN UPDATE emp\n" +
                "  SET Update_id = NULL\n" +
                "  WHERE Update_id = Integritypackage.Updateseq;\n" +
                "END;\n" +
                "/", format);
    }


}
