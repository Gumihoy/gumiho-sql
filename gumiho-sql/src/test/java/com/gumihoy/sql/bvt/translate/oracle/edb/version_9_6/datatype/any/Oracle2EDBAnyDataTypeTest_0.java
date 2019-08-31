package com.gumihoy.sql.bvt.translate.oracle.edb.version_9_6.datatype.any;

import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class Oracle2EDBAnyDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 ANYDATA ,\n" +
                "  c2 SYS.ANYDATA,\n" +
                "  c3 ANYTYPE,\n" +
                "  c4 SYS.ANYTYPE,\n" +
                "  c5 ANYDATASET,\n" +
                "  c6 SYS.ANYDATASET\n" +
                ");";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.EDB.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToEDB(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 ANYDATA,\n" +
                "\tc2 SYS.ANYDATA,\n" +
                "\tc3 ANYTYPE,\n" +
                "\tc4 SYS.ANYTYPE,\n" +
                "\tc5 ANYDATASET,\n" +
                "\tc6 SYS.ANYDATASET\n" +
                ");", result.targetSql);
    }
}
