package com.gumihoy.sql.bvt.dialect.mysql.dml.insert;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-06.
 */
public class MySQLInsertTest_4_Column {

    @Test
    public void test_0() {
        String sql = "INSERT INTO tbl_name (col1,col2) VALUES(15,col1*2);";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("INSERT INTO tbl_name (col1, col2)\n" +
                "VALUES (15, col1 * 2);", format);
    }


}
