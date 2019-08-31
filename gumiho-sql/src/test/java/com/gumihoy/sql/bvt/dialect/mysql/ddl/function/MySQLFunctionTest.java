package com.gumihoy.sql.bvt.dialect.mysql.ddl.function;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-29.
 */
public class MySQLFunctionTest {

    @Test
    public void test_0() {
        String sql = "SELECT COUNT(col1) AS col2 FROM t GROUP BY col2 HAVING col2 = 2;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE new_tbl LIKE orig_tbl;", format);
    }
}
