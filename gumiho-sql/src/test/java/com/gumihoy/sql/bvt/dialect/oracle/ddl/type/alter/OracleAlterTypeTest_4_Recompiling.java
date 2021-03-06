package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTypeTest_4_Recompiling {

    @Test
    public void test_0() {
        String sql = "ALTER TYPE cust_address_typ2 COMPILE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TYPE cust_address_typ2\n" +
                "\tCOMPILE;", format);
    }


}
