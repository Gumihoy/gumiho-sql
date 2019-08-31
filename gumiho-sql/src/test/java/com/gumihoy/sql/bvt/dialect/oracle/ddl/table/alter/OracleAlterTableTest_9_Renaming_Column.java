package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_9_Renaming_Column {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE customers\n" +
                "   RENAME COLUMN credit_limit TO credit_amount;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE customers\n" +
                "   RENAME COLUMN credit_limit TO credit_amount;", format);
    }

}
