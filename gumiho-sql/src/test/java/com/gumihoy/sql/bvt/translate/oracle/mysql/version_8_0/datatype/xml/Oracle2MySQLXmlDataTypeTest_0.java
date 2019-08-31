package com.gumihoy.sql.bvt.translate.oracle.mysql.version_8_0.datatype.xml;

import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
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
public class Oracle2MySQLXmlDataTypeTest_0 {

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
        config.targetDBVersion = DBVersion.MySQL.VERSION_5_7;
        SQLTransformResult result = SQLTransformUtils.oracleToMySQL(sql, config);

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
