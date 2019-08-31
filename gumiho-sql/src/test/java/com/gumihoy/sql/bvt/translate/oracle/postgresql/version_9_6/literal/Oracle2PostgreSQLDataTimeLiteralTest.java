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
public class Oracle2PostgreSQLDataTimeLiteralTest {

    @Test
    public void test_0() {
        String sql = "select date '2012-12-31', timestamp '1997-01-31 09:26:50.124',  TIMESTAMP '2009-10-29 01:30:00' AT TIME ZONE 'US/Pacific';";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);

        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT DATE '2012-12-31', TIMESTAMP '1997-01-31 09:26:50.124',\n" +
                "\tTIMESTAMP '2009-10-29 01:30:00' AT TIME ZONE 'US/Pacific';", result.targetSql);
    }

}
