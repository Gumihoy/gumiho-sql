package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateViewTest_4_Join {

    @Test
    public void test_0() {
        String sql = "CREATE VIEW locations_view AS\n" +
                "   SELECT d.department_id, d.department_name, l.location_id, l.city\n" +
                "   FROM departments d, locations l\n" +
                "   WHERE d.location_id = l.location_id;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW locations_view AS\n" +
                "   SELECT d.department_id, d.department_name, l.location_id, l.city\n" +
                "   FROM departments d, locations l\n" +
                "   WHERE d.location_id = l.location_id;", format);
    }

}
