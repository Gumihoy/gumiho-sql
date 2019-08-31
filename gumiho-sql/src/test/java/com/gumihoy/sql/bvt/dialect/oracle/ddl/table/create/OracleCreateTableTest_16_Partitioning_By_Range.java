package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_16_Partitioning_By_Range {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE range_sales\n" +
                "    ( prod_id        NUMBER(6)\n" +
                "    , cust_id        NUMBER\n" +
                "    , time_id        DATE\n" +
                "    , channel_id     CHAR(1)\n" +
                "    , promo_id       NUMBER(6)\n" +
                "    , quantity_sold  NUMBER(3)\n" +
                "    , amount_sold         NUMBER(10,2)\n" +
                "    ) \n" +
                "PARTITION BY RANGE (time_id)\n" +
                "  (PARTITION SALES_Q1_1998 VALUES LESS THAN (TO_DATE('01-APR-1998','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q2_1998 VALUES LESS THAN (TO_DATE('01-JUL-1998','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q3_1998 VALUES LESS THAN (TO_DATE('01-OCT-1998','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q4_1998 VALUES LESS THAN (TO_DATE('01-JAN-1999','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q1_1999 VALUES LESS THAN (TO_DATE('01-APR-1999','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q2_1999 VALUES LESS THAN (TO_DATE('01-JUL-1999','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q3_1999 VALUES LESS THAN (TO_DATE('01-OCT-1999','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q4_1999 VALUES LESS THAN (TO_DATE('01-JAN-2000','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q1_2000 VALUES LESS THAN (TO_DATE('01-APR-2000','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q2_2000 VALUES LESS THAN (TO_DATE('01-JUL-2000','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q3_2000 VALUES LESS THAN (TO_DATE('01-OCT-2000','DD-MON-YYYY')),\n" +
                "   PARTITION SALES_Q4_2000 VALUES LESS THAN (MAXVALUE))\n" +
                ";";

        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE range_sales (\n" +
                "\tprod_id NUMBER(6),\n" +
                "\tcust_id NUMBER,\n" +
                "\ttime_id DATE,\n" +
                "\tchannel_id CHAR(1),\n" +
                "\tpromo_id NUMBER(6),\n" +
                "\tquantity_sold NUMBER(3),\n" +
                "\tamount_sold NUMBER(10, 2)\n" +
                ")\n" +
                "PARTITION BY RANGE (time_id) (\n" +
                "\tPARTITION SALES_Q1_1998 VALUES LESS THAN (TO_DATE('01-APR-1998', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q2_1998 VALUES LESS THAN (TO_DATE('01-JUL-1998', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q3_1998 VALUES LESS THAN (TO_DATE('01-OCT-1998', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q4_1998 VALUES LESS THAN (TO_DATE('01-JAN-1999', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q1_1999 VALUES LESS THAN (TO_DATE('01-APR-1999', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q2_1999 VALUES LESS THAN (TO_DATE('01-JUL-1999', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q3_1999 VALUES LESS THAN (TO_DATE('01-OCT-1999', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q4_1999 VALUES LESS THAN (TO_DATE('01-JAN-2000', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q1_2000 VALUES LESS THAN (TO_DATE('01-APR-2000', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q2_2000 VALUES LESS THAN (TO_DATE('01-JUL-2000', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q3_2000 VALUES LESS THAN (TO_DATE('01-OCT-2000', 'DD-MON-YYYY')),\n" +
                "\tPARTITION SALES_Q4_2000 VALUES LESS THAN (MAXVALUE)\n" +
                ");", format);
    }


    @Test
    public void test_1() {
        String sql = "CREATE TABLE empl_h  \n" +
                "  (  \n" +
                "     employee_id  NUMBER(6) PRIMARY KEY,  \n" +
                "     first_name   VARCHAR2(20),  \n" +
                "     last_name    VARCHAR2(25),  \n" +
                "     email        VARCHAR2(25),  \n" +
                "     phone_number VARCHAR2(20),  \n" +
                "     hire_date    DATE DEFAULT SYSDATE,  \n" +
                "     job_id       VARCHAR2(10),  \n" +
                "     salary       NUMBER(8, 2),  \n" +
                "     part_name    VARCHAR2(25)  \n" +
                "  ) PARTITION BY RANGE (hire_date) (  \n" +
                "PARTITION hire_q1 VALUES less than(to_date('01-APR-2014', 'DD-MON-YYYY')),   \n" +
                "PARTITION hire_q2 VALUES less than(to_date('01-JUL-2014', 'DD-MON-YYYY')),   \n" +
                "PARTITION hire_q3 VALUES less than(to_date('01-OCT-2014', 'DD-MON-YYYY')),   \n" +
                "PARTITION hire_q4 VALUES less than(to_date('01-JAN-2015', 'DD-MON-YYYY'))  \n" +
                ");";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE empl_h (\n" +
                "\temployee_id NUMBER(6) PRIMARY KEY,\n" +
                "\tfirst_name VARCHAR2(20),\n" +
                "\tlast_name VARCHAR2(25),\n" +
                "\temail VARCHAR2(25),\n" +
                "\tphone_number VARCHAR2(20),\n" +
                "\thire_date DATE DEFAULT SYSDATE,\n" +
                "\tjob_id VARCHAR2(10),\n" +
                "\tsalary NUMBER(8, 2),\n" +
                "\tpart_name VARCHAR2(25)\n" +
                ")\n" +
                "PARTITION BY RANGE (hire_date) (\n" +
                "\tPARTITION hire_q1 VALUES LESS THAN (to_date('01-APR-2014', 'DD-MON-YYYY')),\n" +
                "\tPARTITION hire_q2 VALUES LESS THAN (to_date('01-JUL-2014', 'DD-MON-YYYY')),\n" +
                "\tPARTITION hire_q3 VALUES LESS THAN (to_date('01-OCT-2014', 'DD-MON-YYYY')),\n" +
                "\tPARTITION hire_q4 VALUES LESS THAN (to_date('01-JAN-2015', 'DD-MON-YYYY'))\n" +
                ");", format);
    }

    @Test
    public void test_2() {
        String sql = "CREATE TABLE customers_demo (\n" +
                "  customer_id number(6),\n" +
                "  cust_first_name varchar2(20),\n" +
                "  cust_last_name varchar2(20),\n" +
                "  credit_limit number(9,2))\n" +
                "PARTITION BY RANGE (credit_limit)\n" +
                "INTERVAL (1000)\n" +
                "(PARTITION p1 VALUES LESS THAN (5001));";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE customers_demo (\n" +
                "\tcustomer_id NUMBER(6),\n" +
                "\tcust_first_name VARCHAR2(20),\n" +
                "\tcust_last_name VARCHAR2(20),\n" +
                "\tcredit_limit NUMBER(9, 2)\n" +
                ")\n" +
                "PARTITION BY RANGE (credit_limit) INTERVAL (1000) (\n" +
                "\tPARTITION p1 VALUES LESS THAN (5001)\n" +
                ");", format);
    }
}
