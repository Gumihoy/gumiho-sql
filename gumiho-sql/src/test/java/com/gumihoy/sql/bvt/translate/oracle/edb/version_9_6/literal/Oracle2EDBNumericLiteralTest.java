package com.gumihoy.sql.bvt.translate.oracle.edb.version_9_6.literal;

import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class Oracle2EDBNumericLiteralTest {


    @Test
    public void test_0() {
        String sql = "select 1, .2, 3.4, -5, -6.78, +9.10, 1.2E3, 1.2E-3, -1.2E3, -1.2E-3, 1.0f, 2.0d";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.EDB.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToEDB(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT 1, .2, 3.4, -5, -6.78, +9.10, 1.2E3, 1.2E-3, -1.2E3, -1.2E-3, 1.0F, 2.0D", result.targetSql);
    }

}
