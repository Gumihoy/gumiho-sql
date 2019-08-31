package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_18_Working_Default_List_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE list_customers SPLIT PARTITION rest \n" +
                "   VALUES ('MEXICO', 'COLOMBIA')\n" +
                "   INTO (PARTITION south, PARTITION rest);\n";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE list_customers SPLIT PARTITION rest \n" +
                "   VALUES ('MEXICO', 'COLOMBIA')\n" +
                "   INTO (PARTITION south, PARTITION rest);", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE list_customers \n" +
                "   MERGE PARTITIONS asia, rest INTO PARTITION rest;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE list_customers \n" +
                "   MERGE PARTITIONS asia, rest INTO PARTITION rest;", format);
    }


    @Test
    public void test_2() {
        String sql = "ALTER TABLE list_customers SPLIT PARTITION rest \n" +
                "   VALUES ('CHINA', 'THAILAND')\n" +
                "   INTO (PARTITION asia, PARTITION rest);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE list_customers SPLIT PARTITION rest \n" +
                "   VALUES ('CHINA', 'THAILAND')\n" +
                "   INTO (PARTITION asia, PARTITION rest);", format);
    }
}
