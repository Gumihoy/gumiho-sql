package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTypeTest_3_Increasing_Length {

    @Test
    public void test_0() {
        String sql = "ALTER TYPE phone_list_typ\n" +
                "  MODIFY ELEMENT TYPE VARCHAR(64) CASCADE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TYPE phone_list_typ\n" +
                "  MODIFY ELEMENT TYPE VARCHAR(64) CASCADE;", format);
    }


}
