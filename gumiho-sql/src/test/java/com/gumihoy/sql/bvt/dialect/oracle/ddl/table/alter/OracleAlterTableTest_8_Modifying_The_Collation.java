package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_8_Modifying_The_Collation {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE students \n" +
                "  MODIFY (last_name COLLATE BINARY_CI);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE students \n" +
                "  MODIFY (last_name COLLATE BINARY_CI);", format);
    }

}
