package com.gumihoy.sql.bvt.dialect.oracle.funciton;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-18.
 */
public class OracleFunctionTest_Chr {


    @Test
    public void test_0() {
        String sql = "SELECT CHR(67)||CHR(65)||CHR(84) \"Dog\"\n" +
                "  FROM DUAL;";

        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);

        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT CHR(67) || CHR(65) || CHR(84) \"Dog\"\n" +
                "FROM DUAL;", format);
    }


    @Test
    public void test_1() {
        String sql = "SELECT CHR (196 USING NCHAR_CS)\n" +
                "  FROM DUAL; ";

        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);

        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT CHR(196 USING NCHAR_CS)\n" +
                "FROM DUAL;", format);
    }

}
