package com.gumihoy.sql.bvt.translate.oracle.postgresql.version_9_6.literal;

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
public class Oracle2PostgreSQLNumericLiteralTest {


    @Test
    public void test_0() {
        String sql = "select 1, .2, 3.4, -5, -6.78, +9.10, 1.2E3, 1.2E-3, -1.2E3, -1.2E-3, 1.0f, 2.0d";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT 1, .2, 3.4, -5, -6.78, +9.10, 1.2E3, 1.2E-3, -1.2E3, -1.2E-3, 1.0F, 2.0D", result.targetSql);
    }

}
