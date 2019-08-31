package com.gumihoy.sql.bvt.dialect.mysql.datatype.numeric;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLNumericDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 bit ,\n" +
                "  c2 bit(10) ,\n" +
                "  c3 TINYINT ,\n" +
                "  c4 TINYINT(10) UNSIGNED ZEROFILL,\n" +
                "  c5 BOOL,\n" +
                "  c6 BOOLEAN,\n" +
                "  c7 SMALLINT ,\n" +
                "  c8 MEDIUMINT ,\n" +
                "  c9 INT ,\n" +
                "  c10 INTEGER ,\n" +
                "  c11 BIGINT,\n" +
                "  c12 DECIMAL ,\n" +
                "  c11 dec ,\n" +
                "  c12 FLOAT ,\n" +
                "  c13 DOUBLE,\n" +
                "  c14 DOUBLE PRECISION, \n" +
                "  c15 real \n" +
                ");";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 BIT,\n" +
                "\tc2 BIT(10),\n" +
                "\tc3 TINYINT,\n" +
                "\tc4 TINYINT(10) UNSIGNED ZEROFILL,\n" +
                "\tc5 BOOL,\n" +
                "\tc6 BOOLEAN,\n" +
                "\tc7 SMALLINT,\n" +
                "\tc8 MEDIUMINT,\n" +
                "\tc9 INT,\n" +
                "\tc10 INTEGER,\n" +
                "\tc11 BIGINT,\n" +
                "\tc12 DECIMAL,\n" +
                "\tc11 DEC,\n" +
                "\tc12 FLOAT,\n" +
                "\tc13 DOUBLE,\n" +
                "\tc14 DOUBLE PRECISION,\n" +
                "\tc15 REAL\n" +
                ");", format);
    }
}
