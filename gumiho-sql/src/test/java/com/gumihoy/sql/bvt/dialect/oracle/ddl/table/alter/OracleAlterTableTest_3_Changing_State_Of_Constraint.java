package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_3_Changing_State_Of_Constraint {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE employees\n" +
                "   ENABLE VALIDATE CONSTRAINT emp_manager_fk\n" +
                "   EXCEPTIONS INTO exceptions;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees\n" +
                "   ENABLE VALIDATE CONSTRAINT emp_manager_fk\n" +
                "   EXCEPTIONS INTO exceptions;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE employees\n" +
                "   ENABLE NOVALIDATE PRIMARY KEY\n" +
                "   ENABLE NOVALIDATE CONSTRAINT emp_last_name_nn;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees\n" +
                "   ENABLE NOVALIDATE PRIMARY KEY\n" +
                "   ENABLE NOVALIDATE CONSTRAINT emp_last_name_nn;", format);
    }


    @Test
    public void test_2() {
        String sql = "ALTER TABLE locations\n" +
                "   MODIFY PRIMARY KEY DISABLE CASCADE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE locations\n" +
                "   MODIFY PRIMARY KEY DISABLE CASCADE;", format);
    }

}
