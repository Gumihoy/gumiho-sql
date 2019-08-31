package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_4_Temporary {

    @Test
    public void test_0() {
        String sql = "CREATE GLOBAL TEMPORARY TABLE today_sales\n" +
                "   ON COMMIT PRESERVE ROWS \n" +
                "   AS SELECT * FROM orders WHERE order_date = SYSDATE;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE GLOBAL TEMPORARY TABLE today_sales\n" +
                "ON COMMIT PRESERVE ROWS\n" +
                "AS\n" +
                "\tSELECT *\n" +
                "\tFROM orders\n" +
                "\tWHERE order_date = SYSDATE;", format);
    }


}
