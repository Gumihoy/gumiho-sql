package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_29_Adding_Virtual_Table_Column {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE emp2 ADD (income AS (salary + (salary*commission_pct)));";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE emp2 ADD (income AS (salary + (salary*commission_pct)));", format);
    }

}
