package com.gumihoy.sql.bvt.dialect.mysql.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLDeleteTest_5_Limiting {

    @Test
    public void test_0() {
        String sql = "DELETE FROM somelog WHERE user = 'jcole'\n" +
                "ORDER BY timestamp_column LIMIT 1;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM somelog\n" +
                "WHERE user = 'jcole'\n" +
                "ORDER BY timestamp_column\n" +
                "LIMIT 1;", format);
    }


}
