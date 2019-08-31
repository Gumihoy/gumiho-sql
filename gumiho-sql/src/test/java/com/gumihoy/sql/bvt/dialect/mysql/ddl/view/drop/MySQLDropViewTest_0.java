package com.gumihoy.sql.bvt.dialect.mysql.ddl.view.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-26.
 */
public class MySQLDropViewTest_0 {

    @Test
    public void test_0() {
        String sql = "DROP VIEW IF EXISTS t1, t2 RESTRICT";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP VIEW IF EXISTS t1, t2 RESTRICT", format);
    }

}
