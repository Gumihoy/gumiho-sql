package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_24_Truncating_Table_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE print_media_demo\n" +
                "   TRUNCATE PARTITION p1 DROP STORAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_demo\n" +
                "\tTRUNCATE PARTITION p1 DROP STORAGE;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE print_media_demo\n" +
                "   TRUNCATE PARTITION for (p1) DROP STORAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_demo\n" +
                "\tTRUNCATE PARTITION FOR (p1) DROP STORAGE;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE print_media_demo\n" +
                "   TRUNCATE PARTITIONS p1 DROP STORAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_demo\n" +
                "\tTRUNCATE PARTITIONS p1 DROP STORAGE;", format);
    }


    @Test
    public void test_3() {
        String sql = "ALTER TABLE print_media_demo\n" +
                "   TRUNCATE PARTITIONS FOR (p1) DROP STORAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_demo\n" +
                "\tTRUNCATE PARTITIONS FOR (p1) DROP STORAGE;", format);
    }


    @Test
    public void test_4() {
        String sql = "ALTER TABLE print_media_demo\n" +
                "   TRUNCATE SUBPARTITION p1 DROP STORAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_demo\n" +
                "\tTRUNCATE SUBPARTITION p1 DROP STORAGE;", format);
    }

    @Test
    public void test_5() {
        String sql = "ALTER TABLE print_media_demo\n" +
                "   TRUNCATE SUBPARTITION for (p1) DROP STORAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_demo\n" +
                "\tTRUNCATE SUBPARTITION FOR (p1) DROP STORAGE;", format);
    }

    @Test
    public void test_6() {
        String sql = "ALTER TABLE print_media_demo\n" +
                "   TRUNCATE SUBPARTITIONS p1 DROP STORAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_demo\n" +
                "\tTRUNCATE SUBPARTITIONS p1 DROP STORAGE;", format);
    }


    @Test
    public void test_7() {
        String sql = "ALTER TABLE print_media_demo\n" +
                "   TRUNCATE SUBPARTITIONS FOR (p1) DROP STORAGE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_demo\n" +
                "\tTRUNCATE SUBPARTITIONS FOR (p1) DROP STORAGE;", format);
    }
}
