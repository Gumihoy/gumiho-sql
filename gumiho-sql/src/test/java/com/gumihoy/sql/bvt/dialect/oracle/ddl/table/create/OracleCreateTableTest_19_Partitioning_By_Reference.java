package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_19_Partitioning_By_Reference {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE part_order_items (\n" +
                "    order_id        NUMBER(12) PRIMARY KEY,\n" +
                "    line_item_id    NUMBER(3),\n" +
                "    product_id      NUMBER(6) NOT NULL,\n" +
                "    unit_price      NUMBER(8,2),\n" +
                "    quantity        NUMBER(8),\n" +
                "    CONSTRAINT product_id_fk\n" +
                "    FOREIGN KEY (product_id) REFERENCES hash_products(product_id))\n" +
                " PARTITION BY REFERENCE (product_id_fk); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE part_order_items (\n" +
                "\torder_id NUMBER(12) PRIMARY KEY,\n" +
                "\tline_item_id NUMBER(3),\n" +
                "\tproduct_id NUMBER(6) NOT NULL,\n" +
                "\tunit_price NUMBER(8, 2),\n" +
                "\tquantity NUMBER(8),\n" +
                "\tCONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES hash_products(product_id)\n" +
                ")\n" +
                "PARTITION BY REFERENCE (product_id_fk);", format);
    }


}
