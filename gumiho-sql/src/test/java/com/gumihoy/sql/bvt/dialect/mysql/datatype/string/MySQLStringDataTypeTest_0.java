package com.gumihoy.sql.bvt.dialect.mysql.datatype.string;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLStringDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 CHAR(10) ,\n" +
                "  c2 NATIONAL CHAR(10) ,\n" +
                "  c3 VARCHAR(10) ,\n" +
                "  c4 NATIONAL VARCHAR(10) ,\n" +
                "  c5 BINARY ,\n" +
                "  c6 BINARY(20) ,\n" +
                "  c7 TINYBLOB ,\n" +
                "  c8 TINYTEXT ,\n" +
                "  c9 BLOB ,\n" +
                "  c10 TEXT ,\n" +
                "  c11 MEDIUMBLOB ,\n" +
                "  c12 MEDIUMTEXT ,\n" +
                "  c11 LONGBLOB ,\n" +
                "  c12 LONGTEXT ,\n" +
                "  c13 ENUM('value1','value2'),\n" +
                "  c14 SET('value1','value2') \n" +
                ");";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 CHAR(10),\n" +
                "\tc2 NATIONAL CHAR(10),\n" +
                "\tc3 VARCHAR(10),\n" +
                "\tc4 NATIONAL VARCHAR(10),\n" +
                "\tc5 BINARY,\n" +
                "\tc6 BINARY(20),\n" +
                "\tc7 TINYBLOB,\n" +
                "\tc8 TINYTEXT,\n" +
                "\tc9 BLOB,\n" +
                "\tc10 TEXT,\n" +
                "\tc11 MEDIUMBLOB,\n" +
                "\tc12 MEDIUMTEXT,\n" +
                "\tc11 LONGBLOB,\n" +
                "\tc12 LONGTEXT,\n" +
                "\tc13 ENUM('value1', 'value2'),\n" +
                "\tc14 SET('value1', 'value2')\n" +
                ");", format);
    }
}
