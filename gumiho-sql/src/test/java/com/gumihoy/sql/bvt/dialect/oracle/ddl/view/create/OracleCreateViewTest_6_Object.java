package com.gumihoy.sql.bvt.dialect.oracle.ddl.view.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateViewTest_6_Object {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE VIEW oc_inventories OF inventory_typ\n" +
                " WITH OBJECT OID (product_id)\n" +
                " AS SELECT i.product_id,\n" +
                "           warehouse_typ(w.warehouse_id, w.warehouse_name, w.location_id),\n" +
                "           i.quantity_on_hand\n" +
                "    FROM inventories i, warehouses w\n" +
                "    WHERE i.warehouse_id=w.warehouse_id;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE VIEW oc_inventories OF inventory_typ\n" +
                " WITH OBJECT OID (product_id)\n" +
                " AS SELECT i.product_id,\n" +
                "           warehouse_typ(w.warehouse_id, w.warehouse_name, w.location_id),\n" +
                "           i.quantity_on_hand\n" +
                "    FROM inventories i, warehouses w\n" +
                "    WHERE i.warehouse_id=w.warehouse_id;", format);
    }

}
