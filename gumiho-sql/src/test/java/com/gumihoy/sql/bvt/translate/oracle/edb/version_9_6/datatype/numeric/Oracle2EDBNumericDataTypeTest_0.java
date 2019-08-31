package com.gumihoy.sql.bvt.translate.oracle.edb.version_9_6.datatype.numeric;

import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class Oracle2EDBNumericDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 NUMERIC ,\n" +
                "  c2 NUMERIC(10) ,\n" +
                "  c3 NUMERIC(10, 0) ,\n" +
                "  c4 NUMBER,\n" +
                "  c5 NUMBER(10),\n" +
                "  c6 NUMBER(10,0),\n" +
                "  c7 DECIMAL ,\n" +
                "  c8 dec ,\n" +
                "  c9 INT ,\n" +
                "  c10 INTEGER ,\n" +
                "  c11 SMALLINT,\n" +
                "  c12 FLOAT ,\n" +
                "  c13 DOUBLE,\n" +
                "  c14 DOUBLE PRECISION, \n" +
                "  c15 real, \n" +
                "  c16 BINARY_FLOAT, \n" +
                "  c17 BINARY_DOUBLE \n" +
                ");";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.EDB.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToEDB(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 NUMERIC,\n" +
                "\tc2 NUMERIC(10),\n" +
                "\tc3 NUMERIC(10, 0),\n" +
                "\tc4 NUMBER,\n" +
                "\tc5 NUMBER(10),\n" +
                "\tc6 NUMBER(10, 0),\n" +
                "\tc7 DECIMAL,\n" +
                "\tc8 DEC,\n" +
                "\tc9 INT,\n" +
                "\tc10 INTEGER,\n" +
                "\tc11 SMALLINT,\n" +
                "\tc12 FLOAT,\n" +
                "\tc13 DOUBLE,\n" +
                "\tc14 DOUBLE PRECISION,\n" +
                "\tc15 REAL,\n" +
                "\tc16 BINARY_FLOAT,\n" +
                "\tc17 BINARY_DOUBLE\n" +
                ");", result.targetSql);
    }
}
