package com.gumihoy.sql.bvt.dialect.oracle.ddl.function.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateFunctionTest_1_Aggregate {

    @Test
    public void test_0() {
        String sql = "CREATE FUNCTION SecondMax (input NUMBER) RETURN NUMBER\n" +
                "    PARALLEL_ENABLE AGGREGATE USING SecondMaxImpl;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE FUNCTION SecondMax (\n" +
                "\tinput NUMBER\n" +
                ") RETURN NUMBER\n" +
                "PARALLEL_ENABLE\n" +
                "AGGREGATE USING SecondMaxImpl;", format);
    }


}
