package com.gumihoy.sql.bvt.dialect.oracle.datatype.string;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class OracleStringDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 CHAR(10) ,\n" +
                "  c2 CHAR(10 byte) ,\n" +
                "  c3 VARCHAR2(10) ,\n" +
                "  c4 VARCHAR2(10 byte) ,\n" +
                "  c5 nchar ,\n" +
                "  c6 nchar(20) ,\n" +
                "  c7 NVARCHAR2(20) ,\n" +
                "  c8 LONG ,\n" +
                "  c9 LONG RAW,\n" +
                "  c10 RAW(20),\n" +
                "  c11 BLOB,\n" +
                "  c12 CLOB,\n" +
                "  c13 NCLOB,\n" +
                "  c14 BFILE,\n" +
                "  c15 ROWID,\n" +
                "  c16 UROWID(10)\n" +
                ");";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 CHAR(10),\n" +
                "\tc2 CHAR(10 BYTE),\n" +
                "\tc3 VARCHAR2(10),\n" +
                "\tc4 VARCHAR2(10 BYTE),\n" +
                "\tc5 NCHAR,\n" +
                "\tc6 NCHAR(20),\n" +
                "\tc7 NVARCHAR2(20),\n" +
                "\tc8 LONG,\n" +
                "\tc9 LONG RAW,\n" +
                "\tc10 RAW(20),\n" +
                "\tc11 BLOB,\n" +
                "\tc12 CLOB,\n" +
                "\tc13 NCLOB,\n" +
                "\tc14 BFILE,\n" +
                "\tc15 ROWID,\n" +
                "\tc16 UROWID(10)\n" +
                ");", format);
    }
}
