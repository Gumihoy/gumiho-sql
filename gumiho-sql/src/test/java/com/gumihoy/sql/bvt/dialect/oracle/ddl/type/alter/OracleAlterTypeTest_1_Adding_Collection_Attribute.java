package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTypeTest_1_Adding_Collection_Attribute {

    @Test
    public void test_0() {
        String sql = "ALTER TYPE textdoc_typ \n" +
                "   ADD ATTRIBUTE (author VARCHAR2) CASCADE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TYPE textdoc_typ \n" +
                "   ADD ATTRIBUTE (author VARCHAR2) CASCADE;", format);
    }


}
