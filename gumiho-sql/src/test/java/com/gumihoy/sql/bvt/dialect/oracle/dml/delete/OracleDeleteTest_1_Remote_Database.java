package com.gumihoy.sql.bvt.dialect.oracle.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-05.
 */
public class OracleDeleteTest_1_Remote_Database {

    @Test
    public void test_0() {
        String sql = "DELETE FROM hr.locations@remote\n" +
                "   WHERE location_id > 3000;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM hr.locations@remote\n" +
                "WHERE location_id > 3000;", format);
    }


}
