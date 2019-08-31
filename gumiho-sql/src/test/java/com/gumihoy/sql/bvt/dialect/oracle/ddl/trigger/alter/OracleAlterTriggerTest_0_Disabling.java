package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTriggerTest_0_Disabling {

    @Test
    public void test_0() {
        String sql = "ALTER TRIGGER update_job_history DISABLE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TRIGGER update_job_history DISABLE;", format);
    }


}
