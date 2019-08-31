package com.gumihoy.sql.bvt.dialect.mysql.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLAlterTableTest_18_Rename_Index {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE t1 ENGINE = InnoDB;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 ENGINE = InnoDB;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE t1 ROW_FORMAT = COMPRESSED;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 ROW_FORMAT = COMPRESSED;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE t1 AUTO_INCREMENT = 13;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 AUTO_INCREMENT = 13;", format);
    }


    @Test
    public void test_3() {
        String sql = "ALTER TABLE t1 CHARACTER SET = utf8;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 CHARACTER SET = utf8;", format);
    }

    @Test
    public void test_4() {
        String sql = "ALTER TABLE t1 COMMENT = 'New table comment';";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 COMMENT = 'New table comment';", format);
    }

    @Test
    public void test_5() {
        String sql = "ALTER TABLE t1 COMMENT = \"NDB_TABLE=READ_BACKUP=0,PARTITION_BALANCE=FOR_RA_BY_NODE\";";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 COMMENT = \"NDB_TABLE=READ_BACKUP=0,PARTITION_BALANCE=FOR_RA_BY_NODE\";", format);
    }


}