package com.gumihoy.sql.bvt.dialect.oracle.ddl.type.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTypeTest_3_Varray_Type {

    @Test
    public void test_0() {
        String sql = "CREATE TYPE phone_list_typ_demo AS VARRAY(5) OF VARCHAR2(25);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TYPE phone_list_typ_demo AS VARRAY(5) OF VARCHAR2(25);", format);
    }

}
