package com.gumihoy.sql.bvt.dialect.oracle.ddl.function.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterFunctionTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "ALTER FUNCTION oe.get_bal COMPILE;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER FUNCTION oe.get_bal COMPILE;", format);
    }


}
