package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_15_REFERENCING {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER Print_salary_changes\n" +
                "BEFORE UPDATE ON new\n" +
                "REFERENCING new AS Newest\n" +
                "FOR EACH ROW\n" +
                "BEGIN\n" +
                "  :Newest.Field2 := TO_CHAR (:newest.field1);\n" +
                "END;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER Print_salary_changes\n" +
                "BEFORE UPDATE ON new\n" +
                "REFERENCING new AS Newest\n" +
                "FOR EACH ROW\n" +
                "BEGIN\n" +
                "  :Newest.Field2 := TO_CHAR (:newest.field1);\n" +
                "END;", format);
    }


}
