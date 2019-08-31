package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_17_Partitioning_By_List {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE list_customers \n" +
                "   ( customer_id             NUMBER(6)\n" +
                "   , cust_first_name         VARCHAR2(20) \n" +
                "   , cust_last_name          VARCHAR2(20)\n" +
                "   , cust_address            CUST_ADDRESS_TYP\n" +
                "   , nls_territory           VARCHAR2(30)\n" +
                "   , cust_email              VARCHAR2(40))\n" +
                "   PARTITION BY LIST (nls_territory) (\n" +
                "   PARTITION asia VALUES ('CHINA', 'THAILAND'),\n" +
                "   PARTITION europe VALUES ('GERMANY', 'ITALY', 'SWITZERLAND'),\n" +
                "   PARTITION west VALUES ('AMERICA'),\n" +
                "   PARTITION east VALUES ('INDIA'),\n" +
                "   PARTITION rest VALUES (DEFAULT));";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE list_customers (\n" +
                "\tcustomer_id NUMBER(6),\n" +
                "\tcust_first_name VARCHAR2(20),\n" +
                "\tcust_last_name VARCHAR2(20),\n" +
                "\tcust_address CUST_ADDRESS_TYP,\n" +
                "\tnls_territory VARCHAR2(30),\n" +
                "\tcust_email VARCHAR2(40)\n" +
                ")\n" +
                "PARTITION BY LIST (nls_territory) (\n" +
                "\tPARTITION asia VALUES ('CHINA', 'THAILAND'),\n" +
                "\tPARTITION europe VALUES ('GERMANY', 'ITALY', 'SWITZERLAND'),\n" +
                "\tPARTITION west VALUES ('AMERICA'),\n" +
                "\tPARTITION east VALUES ('INDIA'),\n" +
                "\tPARTITION rest VALUES (DEFAULT)\n" +
                ");", format);
    }


}
