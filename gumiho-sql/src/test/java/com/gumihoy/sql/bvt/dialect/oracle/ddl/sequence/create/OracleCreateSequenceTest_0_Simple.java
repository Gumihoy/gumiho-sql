package com.gumihoy.sql.bvt.dialect.oracle.ddl.sequence.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateSequenceTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE SEQUENCE customers_seq\n" +
                " START WITH     1000\n" +
                " INCREMENT BY   1\n" +
                " NOCACHE\n" +
                " NOCYCLE;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SEQUENCE customers_seq\n" +
                "\tSTART WITH 1000\n" +
                "\tINCREMENT BY 1\n" +
                "\tNOCACHE\n" +
                "\tNOCYCLE;", format);
    }


}
