package com.gumihoy.sql.bvt.dialect.mysql.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLUpdateTest_0_Quantifier {

    @Test
    public void test_0() {
        String sql = "UPDATE LOW_PRIORITY t1 SET col1 = col1 + 1;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE LOW_PRIORITY t1\n" +
                "SET col1 = col1 + 1;", format);
    }

    @Test
    public void test_1() {
        String sql = "UPDATE IGNORE t1 SET col1 = col1 + 1;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE IGNORE t1\n" +
                "SET col1 = col1 + 1;", format);
    }

    @Test
    public void test_2() {
        String sql = "UPDATE LOW_PRIORITY IGNORE t1 SET col1 = col1 + 1;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE LOW_PRIORITY IGNORE t1\n" +
                "SET col1 = col1 + 1;", format);
    }

}
