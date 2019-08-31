package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_15_XMLType_Column {

//    @Test
//    public void test_0() {
//        String sql = "CREATE TABLE xwarehouses (\n" +
//                "   warehouse_id        NUMBER,\n" +
//                "   warehouse_spec      XMLTYPE)\n" +
//                "   XMLTYPE warehouse_spec STORE AS CLOB\n" +
//                "   (TABLESPACE example\n" +
//                "    STORAGE (INITIAL 6144)\n" +
//                "    CHUNK 4000\n" +
//                "    NOCACHE LOGGING);";
//        System.out.println(sql);
//        String format = SQLUtils.format(sql, DBType.Oracle);
//        System.out.println("----------------");
//        System.out.println(format);
//        Assert.assertEquals("CREATE TABLE xwarehouses (\n" +
//                "   warehouse_id        NUMBER,\n" +
//                "   warehouse_spec      XMLTYPE)\n" +
//                "   XMLTYPE warehouse_spec STORE AS CLOB\n" +
//                "   (TABLESPACE example\n" +
//                "    STORAGE (INITIAL 6144)\n" +
//                "    CHUNK 4000\n" +
//                "    NOCACHE LOGGING);", format);
//    }


    @Test
    public void test_1() {
        String sql = "CREATE TABLE xwarehouses (\n" +
                "   warehouse_id    NUMBER,\n" +
                "   warehouse_spec  XMLTYPE)\n" +
                "   XMLTYPE warehouse_spec STORE AS OBJECT RELATIONAL\n" +
                "      XMLSCHEMA \"http://www.example.com/xwarehouses.xsd\"\n" +
                "      ELEMENT \"Warehouse\";";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE xwarehouses (\n" +
                "   warehouse_id    NUMBER,\n" +
                "   warehouse_spec  XMLTYPE)\n" +
                "   XMLTYPE warehouse_spec STORE AS OBJECT RELATIONAL\n" +
                "      XMLSCHEMA \"http://www.example.com/xwarehouses.xsd\"\n" +
                "      ELEMENT \"Warehouse\";", format);
    }

//    @Test
//    public void test_2() {
//        String sql = "CREATE TABLE xwarehouses (\n" +
//                "  warehouse_id   NUMBER,\n" +
//                "  warehouse_spec XMLTYPE)\n" +
//                "  XMLTYPE        warehouse_spec STORE AS SECUREFILE CLOB\n" +
//                "  (TABLESPACE auto_seg_ts\n" +
//                "  STORAGE (INITIAL 6144)\n" +
//                "  CACHE);";
//        System.out.println(sql);
//        String format = SQLUtils.format(sql, DBType.Oracle);
//        System.out.println("----------------");
//        System.out.println(format);
//        Assert.assertEquals("CREATE TABLE xwarehouses (\n" +
//                "  warehouse_id   NUMBER,\n" +
//                "  warehouse_spec XMLTYPE)\n" +
//                "  XMLTYPE        warehouse_spec STORE AS SECUREFILE CLOB\n" +
//                "  (TABLESPACE auto_seg_ts\n" +
//                "  STORAGE (INITIAL 6144)\n" +
//                "  CACHE);", format);
//    }
}
