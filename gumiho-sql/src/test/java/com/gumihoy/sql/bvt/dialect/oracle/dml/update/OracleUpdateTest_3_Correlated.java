package com.gumihoy.sql.bvt.dialect.oracle.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleUpdateTest_3_Correlated {

    @Test
    public void test_0() {
        String sql = "UPDATE TABLE(SELECT h.people FROM hr_info h\n" +
                "   WHERE h.department_id = 280) p\n" +
                "   SET p.salary = p.salary + 100;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE TABLE(\n" +
                "\tSELECT h.people\n" +
                "\tFROM hr_info h\n" +
                "\tWHERE h.department_id = 280\n" +
                ") p\n" +
                "SET p.salary = p.salary + 100;", format);
    }

}
