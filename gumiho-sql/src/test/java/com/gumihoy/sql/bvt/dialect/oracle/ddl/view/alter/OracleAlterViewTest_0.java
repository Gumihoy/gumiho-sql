package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterViewTest_0 {

    @Test
    public void test_0() {
        String sql = "ALTER VIEW customer_ro\n" +
                "    COMPILE; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER VIEW customer_ro\n" +
                "    COMPILE; ", format);
    }

}
