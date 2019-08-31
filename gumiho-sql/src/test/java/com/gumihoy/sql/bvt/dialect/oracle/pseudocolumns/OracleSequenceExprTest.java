package com.gumihoy.sql.bvt.dialect.oracle.pseudocolumns;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleSequenceExprTest {

    @Test
    public void test_0() {
        String sql = "SELECT employees_seq.currval \n" +
                "  FROM DUAL;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT employees_seq.CURRVAL\n" +
                "FROM DUAL;", format);
    }

    @Test
    public void test_1() {
        String sql = "SELECT employees_seq.nextval \n" +
                "  FROM DUAL;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT employees_seq.NEXTVAL\n" +
                "FROM DUAL;", format);
    }

}
