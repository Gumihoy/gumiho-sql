package com.gumihoy.sql.bvt.dialect.oracle.ddl.procedure.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleDropProcedureTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "DROP PROCEDURE hr.remove_emp; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP PROCEDURE hr.remove_emp;", format);
    }


}
