package com.gumihoy.sql.bvt.dialect.oracle.ddl.procedure.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterProcedureTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "ALTER PROCEDURE hr.remove_emp COMPILE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER PROCEDURE hr.remove_emp COMPILE;", format);
    }


}
