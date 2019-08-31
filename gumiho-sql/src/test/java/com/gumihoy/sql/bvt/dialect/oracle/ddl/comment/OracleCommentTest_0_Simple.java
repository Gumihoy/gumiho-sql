package com.gumihoy.sql.bvt.dialect.oracle.ddl.comment;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCommentTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "COMMENT ON COLUMN employees.job_id \n" +
                "   IS 'abbreviated job title';";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("COMMENT ON COLUMN employees.job_id IS 'abbreviated job title';", format);
    }

    @Test
    public void test_1() {
        String sql = "COMMENT ON COLUMN employees.job_id IS ''; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("COMMENT ON COLUMN employees.job_id IS '';", format);
    }

}
