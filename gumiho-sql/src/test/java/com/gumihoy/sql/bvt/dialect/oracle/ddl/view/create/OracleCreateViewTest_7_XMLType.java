package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateViewTest_7_XMLType {

    @Test
    public void test_0() {
        String sql = "CREATE VIEW warehouse_view AS\n" +
                "   SELECT VALUE(p) AS warehouse_xml\n" +
                "   FROM xwarehouses p;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE VIEW warehouse_view AS\n" +
                "   SELECT VALUE(p) AS warehouse_xml\n" +
                "   FROM xwarehouses p;", format);
    }

}
