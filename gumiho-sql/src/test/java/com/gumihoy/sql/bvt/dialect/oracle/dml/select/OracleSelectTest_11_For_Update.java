package com.gumihoy.sql.bvt.dialect.oracle.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleSelectTest_11_For_Update {

    @Test
    public void test_0() {
        String sql = "SELECT department_id, department_name, location_id\n" +
                "   FROM departments WHERE location_id < 2000 WITH CHECK OPTION";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT department_id, department_name, location_id\n" +
                "   FROM departments WHERE location_id < 2000 WITH CHECK OPTION", format);
    }




}
