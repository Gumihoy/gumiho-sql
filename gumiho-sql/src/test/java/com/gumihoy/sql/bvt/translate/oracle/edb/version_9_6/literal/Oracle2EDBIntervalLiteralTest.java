package com.gumihoy.sql.bvt.translate.oracle.edb.version_9_6.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class Oracle2EDBIntervalLiteralTest {

    @Test
    public void test_0() {
        String sql = "select INTERVAL '123-2' YEAR(3) TO MONTH, INTERVAL '123' YEAR(3), INTERVAL '300' MONTH(3), INTERVAL '4' YEAR, INTERVAL '50' MONTH, INTERVAL '5-3' YEAR TO MONTH + INTERVAL '6-11' YEAR TO MONTH;";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.EDB.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToEDB(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT INTERVAL '123-2' YEAR(3) TO MONTH, INTERVAL '123' YEAR(3),\n" +
                "\tINTERVAL '300' MONTH(3), INTERVAL '4' YEAR, INTERVAL '50' MONTH,\n" +
                "\tINTERVAL '5-3' YEAR TO MONTH + INTERVAL '6-11' YEAR TO MONTH;", result.targetSql);
    }

}
