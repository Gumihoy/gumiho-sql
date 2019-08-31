package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_28_Adding_Table_Column {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE countries \n" +
                "   ADD (duty_pct     NUMBER(2,2)  CHECK (duty_pct < 10.5),\n" +
                "        visa_needed  VARCHAR2(3)); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE countries\n" +
                "\tADD (duty_pct NUMBER(2, 2) CHECK (duty_pct < 10.5), visa_needed VARCHAR2(3));", format);
    }

}
