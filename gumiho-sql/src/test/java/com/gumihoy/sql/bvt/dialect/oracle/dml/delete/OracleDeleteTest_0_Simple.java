package com.gumihoy.sql.bvt.dialect.oracle.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-05.
 */
public class OracleDeleteTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "DELETE FROM product_descriptions\n" +
                "   WHERE language_id = 'AR';";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM product_descriptions\n" +
                "WHERE language_id = 'AR';", format);
    }

    @Test
    public void test_1() {
        String sql = "DELETE FROM employees\n" +
                "   WHERE job_id = 'SA_REP'\n" +
                "   AND commission_pct < .2;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM employees\n" +
                "WHERE job_id = 'SA_REP' AND commission_pct < .2;", format);
    }

    @Test
    public void test_2() {
        String sql = "DELETE FROM (SELECT * FROM employees)\n" +
                "   WHERE job_id = 'SA_REP'\n" +
                "   AND commission_pct < .2;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM (\n" +
                "\tSELECT *\n" +
                "\tFROM employees\n" +
                ")\n" +
                "WHERE job_id = 'SA_REP' AND commission_pct < .2;", format);
    }

}
