package com.gumihoy.sql.bvt.dialect.oracle.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-05.
 */
public class OracleDeleteTest_2_Nested_Table {

    @Test
    public void test_0() {
        String sql = "DELETE TABLE(SELECT h.people FROM hr_info h\n" +
                "   WHERE h.department_id = 280) p\n" +
                "   WHERE p.salary > 1700;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE TABLE(\n" +
                "\tSELECT h.people\n" +
                "\tFROM hr_info h\n" +
                "\tWHERE h.department_id = 280\n" +
                ") p\n" +
                "WHERE p.salary > 1700;", format);
    }


}
