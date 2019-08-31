package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_14_Conditional_Trigger {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER print_salary_changes\n" +
                "  BEFORE DELETE OR INSERT OR UPDATE ON employees\n" +
                "  FOR EACH ROW\n" +
                "  WHEN (NEW.job_id <> 'AD_PRES')  -- do not print information about President\n" +
                "DECLARE\n" +
                "  sal_diff  NUMBER;\n" +
                "BEGIN\n" +
                "  sal_diff  := :NEW.salary  - :OLD.salary;\n" +
                "  DBMS_OUTPUT.PUT(:NEW.last_name || ': ');\n" +
                "  DBMS_OUTPUT.PUT('Old salary = ' || :OLD.salary || ', ');\n" +
                "  DBMS_OUTPUT.PUT('New salary = ' || :NEW.salary || ', ');\n" +
                "  DBMS_OUTPUT.PUT_LINE('Difference: ' || sal_diff);\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER print_salary_changes\n" +
                "\tBEFORE\n" +
                "\t\tDELETE OR\n" +
                "\t\tINSERT OR\n" +
                "\t\tUPDATE\n" +
                "\tON employees\n" +
                "\tWHEN (NEW.job_id <> 'AD_PRES')\n" +
                "\tDECLARE\n" +
                "\t\tsal_diff NUMBER;\n" +
                "\tBEGIN\n" +
                "\t\tsal_diff := :NEW.salary - :OLD.salary;\n" +
                "\t\tDBMS_OUTPUT.PUT(:NEW.last_name || ': ');\n" +
                "\t\tDBMS_OUTPUT.PUT('Old salary = ' || :OLD.salary || ', ');\n" +
                "\t\tDBMS_OUTPUT.PUT('New salary = ' || :NEW.salary || ', ');\n" +
                "\t\tDBMS_OUTPUT.PUT_LINE('Difference: ' || sal_diff);\n" +
                "\tEND;", format);
    }


}
