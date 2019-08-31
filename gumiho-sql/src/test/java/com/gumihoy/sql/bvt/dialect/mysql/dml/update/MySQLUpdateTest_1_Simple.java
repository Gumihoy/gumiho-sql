package com.gumihoy.sql.bvt.dialect.mysql.dml.update;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLUpdateTest_1_Simple {

    @Test
    public void test_0() {
        String sql = "UPDATE t1 SET col1 = col1 + 1, col2 = col1;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("UPDATE t1\n" +
                "SET col1 = col1 + 1, col2 = col1;", format);
    }


}
