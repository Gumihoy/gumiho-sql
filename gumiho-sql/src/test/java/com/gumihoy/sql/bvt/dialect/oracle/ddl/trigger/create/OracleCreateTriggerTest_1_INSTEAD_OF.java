package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_1_INSTEAD_OF {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER order_info_insert\n" +
                "   INSTEAD OF INSERT ON order_info\n" +
                "   DECLARE\n" +
                "     duplicate_info EXCEPTION;\n" +
                "     PRAGMA EXCEPTION_INIT (duplicate_info, -00001);\n" +
                "   BEGIN\n" +
                "     INSERT INTO customers\n" +
                "       (customer_id, cust_last_name, cust_first_name)\n" +
                "     VALUES (\n" +
                "     :new.customer_id,\n" +
                "     :new.cust_last_name,\n" +
                "     :new.cust_first_name);\n" +
                "   INSERT INTO orders (order_id, order_date, customer_id)\n" +
                "   VALUES (\n" +
                "     :new.order_id,\n" +
                "     :new.order_date,\n" +
                "     :new.customer_id);\n" +
                "   EXCEPTION\n" +
                "     WHEN duplicate_info THEN\n" +
                "       RAISE_APPLICATION_ERROR (\n" +
                "         num=> -20107,\n" +
                "         msg=> 'Duplicate customer or order ID');\n" +
                "   END order_info_insert;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER order_info_insert\n" +
                "   INSTEAD OF INSERT ON order_info\n" +
                "   DECLARE\n" +
                "     duplicate_info EXCEPTION;\n" +
                "     PRAGMA EXCEPTION_INIT (duplicate_info, -00001);\n" +
                "   BEGIN\n" +
                "     INSERT INTO customers\n" +
                "       (customer_id, cust_last_name, cust_first_name)\n" +
                "     VALUES (\n" +
                "     :new.customer_id,\n" +
                "     :new.cust_last_name,\n" +
                "     :new.cust_first_name);\n" +
                "   INSERT INTO orders (order_id, order_date, customer_id)\n" +
                "   VALUES (\n" +
                "     :new.order_id,\n" +
                "     :new.order_date,\n" +
                "     :new.customer_id);\n" +
                "   EXCEPTION\n" +
                "     WHEN duplicate_info THEN\n" +
                "       RAISE_APPLICATION_ERROR (\n" +
                "         num=> -20107,\n" +
                "         msg=> 'Duplicate customer or order ID');\n" +
                "   END order_info_insert;\n" +
                "/", format);
    }


}
