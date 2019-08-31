package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_3_Compound {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER maintain_employee_salaries\n" +
                "  FOR UPDATE OF salary ON employees\n" +
                "    COMPOUND TRIGGER\n" +
                "\n" +
                "-- Declarative Part:\n" +
                "-- Choose small threshhold value to show how example works:\n" +
                "  threshhold CONSTANT SIMPLE_INTEGER := 7;\n" +
                "\n" +
                "  TYPE salaries_t IS TABLE OF employee_salaries%ROWTYPE INDEX BY SIMPLE_INTEGER;\n" +
                "  salaries  salaries_t;\n" +
                "  idx       SIMPLE_INTEGER := 0;\n" +
                "\n" +
                "  PROCEDURE flush_array IS\n" +
                "    n CONSTANT SIMPLE_INTEGER := salaries.count();\n" +
                "  BEGIN\n" +
                "    FORALL j IN 1..n\n" +
                "      INSERT INTO employee_salaries VALUES salaries(j);\n" +
                "    salaries.delete();\n" +
                "    idx := 0;\n" +
                "    DBMS_OUTPUT.PUT_LINE('Flushed ' || n || ' rows');\n" +
                "  END flush_array;\n" +
                "\n" +
                "  -- AFTER EACH ROW Section:\n" +
                "\n" +
                "  AFTER EACH ROW IS\n" +
                "  BEGIN\n" +
                "    idx := idx + 1;\n" +
                "    salaries(idx).employee_id := :NEW.employee_id;\n" +
                "    salaries(idx).change_date := SYSTIMESTAMP;\n" +
                "    salaries(idx).salary := :NEW.salary;\n" +
                "    IF idx >= threshhold THEN\n" +
                "      flush_array();\n" +
                "    END IF;\n" +
                "  END AFTER EACH ROW;\n" +
                "\n" +
                "  -- AFTER STATEMENT Section:\n" +
                "\n" +
                "  AFTER STATEMENT IS\n" +
                "  BEGIN\n" +
                "    flush_array();\n" +
                "  END AFTER STATEMENT;\n" +
                "END maintain_employee_salaries;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER maintain_employee_salaries\n" +
                "\tFOR\n" +
                "\t\tUPDATE OF salary\n" +
                "\tON employees\n" +
                "\tCOMPOUND TRIGGER\n" +
                "\t\tthreshhold CONSTANT SIMPLE_INTEGER := 7;\n" +
                "\t\tTYPE salaries_t IS TABLE OF employee_salaries%ROWTYPE INDEX BY SIMPLE_INTEGER;\n" +
                "\t\tsalaries salaries_t;\n" +
                "\t\tidx SIMPLE_INTEGER := 0;\n" +
                "\t\tPROCEDURE flush_array IS\n" +
                "\t\t\tn CONSTANT SIMPLE_INTEGER := salaries.count();\n" +
                "\t\t\tBEGIN\n" +
                "\t\t\t\tFORALL j IN 1 .. n\n" +
                "\t\t\t\t\tINSERT INTO employee_salaries\n" +
                "\t\t\t\t\tVALUES salaries(j);\n" +
                "\t\t\t\tsalaries.delete();\n" +
                "\t\t\t\tidx := 0;\n" +
                "\t\t\t\tDBMS_OUTPUT.PUT_LINE('Flushed ' || n || ' rows');\n" +
                "\t\t\tEND flush_array;\n" +
                "\t\tAFTER EACH ROW IS BEGIN\n" +
                "\t\t\tidx := idx + 1;\n" +
                "\t\t\tsalaries(idx).employee_id := :NEW.employee_id;\n" +
                "\t\t\tsalaries(idx).change_date := SYSTIMESTAMP;\n" +
                "\t\t\tsalaries(idx).salary := :NEW.salary;\n" +
                "\t\t\tIF idx >= threshhold THEN\n" +
                "\t\t\t\tflush_array();\n" +
                "\t\t\tEND IF;\n" +
                "\t\tEND AFTER EACH ROW;\n" +
                "\t\tAFTER STATEMENT IS BEGIN\n" +
                "\t\t\tflush_array();\n" +
                "\t\tEND AFTER STATEMENT;\n" +
                "\tEND maintain_employee_salaries;", format);
    }


}
