package com.gumihoy.sql.bvt.translate.oracle.edb.version_9_6.datatype.interval;

import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class Oracle2EDBIntervalDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 INTERVAL YEAR  to month,\n" +
                "  c2 INTERVAL YEAR(2)  to month,\n" +
                "  c3 INTERVAL day  to second,\n" +
                "  c4 INTERVAL day(2)  to second,\n" +
                "  c5 INTERVAL day(2)  to second(3)\n" +
                ");";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.EDB.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToEDB(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 INTERVAL YEAR TO MONTH,\n" +
                "\tc2 INTERVAL YEAR (2) TO MONTH,\n" +
                "\tc3 INTERVAL DAY TO SECOND,\n" +
                "\tc4 INTERVAL DAY (2) TO SECOND,\n" +
                "\tc5 INTERVAL DAY (2) TO SECOND (3)\n" +
                ");", result.targetSql);
    }
}
