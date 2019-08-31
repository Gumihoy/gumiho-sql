package com.gumihoy.sql.bvt.dialect.oracle.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleUpdateTest_2_Object_Table {

    @Test
    public void test_0() {
        String sql = "UPDATE people_demo1 p SET VALUE(p) =\n" +
                "   (SELECT VALUE(q) FROM people_demo2 q\n" +
                "    WHERE p.department_id = q.department_id)\n" +
                "   WHERE p.department_id = 10;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE people_demo1 p\n" +
                "SET VALUE(p) = (\n" +
                "\t\tSELECT VALUE(q)\n" +
                "\t\tFROM people_demo2 q\n" +
                "\t\tWHERE p.department_id = q.department_id\n" +
                "\t)\n" +
                "WHERE p.department_id = 10;", format);
    }

}
