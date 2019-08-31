package com.gumihoy.sql.bvt.dialect.oracle.ddl.schema.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateSchemaTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE SCHEMA AUTHORIZATION oe\n" +
                "   CREATE TABLE new_product \n" +
                "      (color VARCHAR2(10)  PRIMARY KEY, quantity NUMBER) \n" +
                "   CREATE VIEW new_product_view \n" +
                "      AS SELECT color, quantity FROM new_product WHERE color = 'RED' \n" +
                "   GRANT select ON new_product_view TO hr; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE SCHEMA AUTHORIZATION oe\n" +
                "   CREATE TABLE new_product \n" +
                "      (color VARCHAR2(10)  PRIMARY KEY, quantity NUMBER) \n" +
                "   CREATE VIEW new_product_view \n" +
                "      AS SELECT color, quantity FROM new_product WHERE color = 'RED' \n" +
                "   GRANT select ON new_product_view TO hr; ", format);
    }


}
