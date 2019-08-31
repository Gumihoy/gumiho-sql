package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_20_Invokes_Java_Subprogram {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER Pre_del_trigger BEFORE DELETE ON Tab \n" +
                "FOR EACH ROW\n" +
                "CALL Before_delete (:OLD.Id, :OLD.Ename)";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER Pre_del_trigger BEFORE DELETE ON Tab \n" +
                "FOR EACH ROW\n" +
                "CALL Before_delete (:OLD.Id, :OLD.Ename)", format);
    }


}
