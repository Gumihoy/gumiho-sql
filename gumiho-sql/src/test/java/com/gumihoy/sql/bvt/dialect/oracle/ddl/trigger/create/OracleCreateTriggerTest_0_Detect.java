package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_0_Detect {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER t\n" +
                "  BEFORE\n" +
                "    INSERT OR\n" +
                "    UPDATE OF salary, department_id OR\n" +
                "    DELETE\n" +
                "  ON employees\n" +
                "BEGIN\n" +
                "  CASE\n" +
                "    WHEN INSERTING THEN\n" +
                "      DBMS_OUTPUT.PUT_LINE('Inserting');\n" +
                "    WHEN UPDATING('salary') THEN\n" +
                "      DBMS_OUTPUT.PUT_LINE('Updating salary');\n" +
                "    WHEN UPDATING('department_id') THEN\n" +
                "      DBMS_OUTPUT.PUT_LINE('Updating department ID');\n" +
                "    WHEN DELETING THEN\n" +
                "      DBMS_OUTPUT.PUT_LINE('Deleting');\n" +
                "  END CASE;\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER t\n" +
                "\tBEFORE\n" +
                "\t\tINSERT OR\n" +
                "\t\tUPDATE OF salary, department_id OR\n" +
                "\t\tDELETE\n" +
                "\tON employees\n" +
                "\tBEGIN\n" +
                "\t\tCASE\n" +
                "\t\t\tWHEN INSERTING THEN\n" +
                "\t\t\t\tDBMS_OUTPUT.PUT_LINE('Inserting');\n" +
                "\t\t\tWHEN UPDATING('salary') THEN\n" +
                "\t\t\t\tDBMS_OUTPUT.PUT_LINE('Updating salary');\n" +
                "\t\t\tWHEN UPDATING('department_id') THEN\n" +
                "\t\t\t\tDBMS_OUTPUT.PUT_LINE('Updating department ID');\n" +
                "\t\t\tWHEN DELETING THEN\n" +
                "\t\t\t\tDBMS_OUTPUT.PUT_LINE('Deleting');\n" +
                "\t\tEND CASE;\n" +
                "\tEND;", format);
    }


}
