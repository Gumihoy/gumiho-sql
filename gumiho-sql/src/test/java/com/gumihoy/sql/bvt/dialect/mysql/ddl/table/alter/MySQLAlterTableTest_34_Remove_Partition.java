package com.gumihoy.sql.bvt.dialect.mysql.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class MySQLAlterTableTest_34_Remove_Partition {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE t1 REMOVE PARTITIONING";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 REMOVE PARTITIONING", format);
    }

}