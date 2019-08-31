package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTypeTest_6_Member_Method {

    @Test
    public void test_0() {
        String sql = "CREATE TYPE demo_typ2 AS OBJECT (a1 NUMBER, \n" +
                "   MEMBER FUNCTION get_square RETURN NUMBER); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TYPE demo_typ2 AS OBJECT (a1 NUMBER, \n" +
                "   MEMBER FUNCTION get_square RETURN NUMBER); ", format);
    }

}
