package com.gumihoy.sql.bvt.dialect.mysql.ddl.trigger.drop;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-26.
 */
public class MySQLDropTriggerTest_0 {

    @Test
    public void test_0() {
        String sql = "DROP TRIGGER IF EXISTS schema_name.trigger_name";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DROP TRIGGER IF EXISTS schema_name.trigger_name", format);
    }


}
