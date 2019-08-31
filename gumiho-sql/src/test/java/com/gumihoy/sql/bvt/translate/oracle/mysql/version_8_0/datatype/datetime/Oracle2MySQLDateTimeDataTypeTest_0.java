package com.gumihoy.sql.bvt.translate.oracle.mysql.version_8_0.datatype.datetime;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class Oracle2MySQLDateTimeDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 DATE ,\n" +
                "  c2 TIMESTAMP,\n" +
                "  c3 TIMESTAMP(10),\n" +
                "  c4 TIMESTAMP(10) with time zone,\n" +
                "  c5 TIMESTAMP(10) with local time zone\n" +
                ");";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.MySQL.VERSION_5_7;
        SQLTransformResult result = SQLTransformUtils.oracleToMySQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 DATE,\n" +
                "\tc2 TIMESTAMP,\n" +
                "\tc3 TIMESTAMP(10),\n" +
                "\tc4 TIMESTAMP(10) WITH TIME ZONE,\n" +
                "\tc5 TIMESTAMP(10) WITH LOCAL TIME ZONE\n" +
                ");", result.targetSql);
    }
}
