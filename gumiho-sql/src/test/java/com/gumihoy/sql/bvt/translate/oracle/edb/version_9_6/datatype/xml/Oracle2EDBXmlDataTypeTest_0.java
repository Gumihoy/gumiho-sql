package com.gumihoy.sql.bvt.translate.oracle.edb.version_9_6.datatype.xml;

import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class Oracle2EDBXmlDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 XMLTYPE ,\n" +
                "  c2 SYS.XMLTYPE,\n" +
                "  c3 URITYPE,\n" +
                "  c4 SYS.URITYPE\n" +
                ");";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);

        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 XMLTYPE,\n" +
                "\tc2 SYS.XMLTYPE,\n" +
                "\tc3 URITYPE,\n" +
                "\tc4 SYS.URITYPE\n" +
                ");", result.targetSql);
    }

}
