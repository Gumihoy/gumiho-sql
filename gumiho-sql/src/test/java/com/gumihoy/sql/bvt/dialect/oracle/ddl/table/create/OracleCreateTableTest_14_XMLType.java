package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_14_XMLType {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE xwarehouses OF XMLTYPE;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE xwarehouses OF XMLTYPE;", format);
    }


    @Test
    public void test_1() {
        String sql = "CREATE TABLE xwarehouses OF XMLTYPE\n" +
                "   XMLSCHEMA \"http://www.example.com/xwarehouses.xsd\"\n" +
                "   ELEMENT \"Warehouse\";";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE xwarehouses OF XMLTYPE\n" +
                "   XMLSCHEMA \"http://www.example.com/xwarehouses.xsd\"\n" +
                "   ELEMENT \"Warehouse\";", format);
    }
}
