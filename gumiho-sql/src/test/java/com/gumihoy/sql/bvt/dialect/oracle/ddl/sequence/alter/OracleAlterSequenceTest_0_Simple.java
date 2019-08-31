package com.gumihoy.sql.bvt.dialect.oracle.ddl.sequence.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterSequenceTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "ALTER SEQUENCE customers_seq \n" +
                "   MAXVALUE 1500;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER SEQUENCE customers_seq\n" +
                "\tMAXVALUE 1500;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER SEQUENCE customers_seq \n" +
                "   CYCLE\n" +
                "   CACHE 5; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER SEQUENCE customers_seq\n" +
                "\tCYCLE\n" +
                "\tCACHE 5;", format);
    }
}
