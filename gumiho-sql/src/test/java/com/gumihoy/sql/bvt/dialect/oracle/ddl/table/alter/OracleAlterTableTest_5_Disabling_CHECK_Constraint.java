package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_5_Disabling_CHECK_Constraint {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE employees ADD CONSTRAINT check_comp \n" +
                "   CHECK (salary + (commission_pct*salary) <= 5000)\n" +
                "   DISABLE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees ADD CONSTRAINT check_comp \n" +
                "   CHECK (salary + (commission_pct*salary) <= 5000)\n" +
                "   DISABLE;", format);
    }

}
