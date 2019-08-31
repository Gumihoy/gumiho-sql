package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.column;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleColumnConstraint_5_Scope_Is {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE customer_addresses (\n" +
                "   add_id NUMBER, \n" +
                "   address REF cust_address_typ_new\n" +
                "   SCOPE IS address_table);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE customer_addresses (\n" +
                "\tadd_id NUMBER,\n" +
                "\taddress REF cust_address_typ_new SCOPE IS address_table\n" +
                ");", format);
    }

}
