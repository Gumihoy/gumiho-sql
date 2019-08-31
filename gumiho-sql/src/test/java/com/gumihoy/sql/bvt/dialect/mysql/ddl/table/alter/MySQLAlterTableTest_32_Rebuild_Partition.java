package com.gumihoy.sql.bvt.dialect.mysql.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLAlterTableTest_32_Rebuild_Partition {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE t1 Rebuild PARTITION p1, p2";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 Rebuild PARTITION p1, p2", format);
    }

}