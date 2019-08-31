package com.gumihoy.sql.bvt.dialect.oracle.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleSelectTest_2_Simple {

    @Test
    public void test_0() {
        String sql = "WITH\n" +
                " FUNCTION get_domain(url VARCHAR2) RETURN VARCHAR2 IS\n" +
                "   pos BINARY_INTEGER;\n" +
                "   len BINARY_INTEGER;\n" +
                " BEGIN\n" +
                "   pos := INSTR(url, 'www.');\n" +
                "   len := INSTR(SUBSTR(url, pos + 4), '.') - 1;\n" +
                "   RETURN SUBSTR(url, pos + 4, len);\n" +
                " END;\n" +
                "SELECT DISTINCT get_domain(catalog_url)\n" +
                "  FROM product_information;\n" +
                "/";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("WITH\n" +
                " FUNCTION get_domain(url VARCHAR2) RETURN VARCHAR2 IS\n" +
                "   pos BINARY_INTEGER;\n" +
                "   len BINARY_INTEGER;\n" +
                " BEGIN\n" +
                "   pos := INSTR(url, 'www.');\n" +
                "   len := INSTR(SUBSTR(url, pos + 4), '.') - 1;\n" +
                "   RETURN SUBSTR(url, pos + 4, len);\n" +
                " END;\n" +
                "SELECT DISTINCT get_domain(catalog_url)\n" +
                "  FROM product_information;\n" +
                "/", format);
    }



}
