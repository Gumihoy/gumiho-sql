package com.gumihoy.sql.bvt.dialect.oracle.ddl.packagebody.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleDropPackageBodyTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "DROP PACKAGE BODY emp_mgmt; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP PACKAGE BODY emp_mgmt;", format);
    }


}
