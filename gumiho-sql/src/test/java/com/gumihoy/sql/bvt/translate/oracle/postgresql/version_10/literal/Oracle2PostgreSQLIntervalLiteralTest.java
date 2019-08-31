package com.gumihoy.sql.bvt.translate.oracle.postgresql.version_10.literal;

import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class Oracle2PostgreSQLIntervalLiteralTest {

    @Test
    public void test_0() {
        String sql = "select INTERVAL '123-2' YEAR(3) TO MONTH, INTERVAL '123' YEAR(3), INTERVAL '300' MONTH(3), INTERVAL '4' YEAR, INTERVAL '50' MONTH, INTERVAL '5-3' YEAR TO MONTH + INTERVAL '6-11' YEAR TO MONTH;";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT INTERVAL '123-2' YEAR(3) TO MONTH, INTERVAL '123' YEAR(3),\n" +
                "\tINTERVAL '300' MONTH(3), INTERVAL '4' YEAR, INTERVAL '50' MONTH,\n" +
                "\tINTERVAL '5-3' YEAR TO MONTH + INTERVAL '6-11' YEAR TO MONTH;", result.targetSql);
    }

}
