package com.gumihoy.sql.bvt.dialect.oracle.datatype.spatial;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class OracleSpatialDataTypeTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 SDO_Geometry ,\n" +
                "  c2 SYS.SDO_Geometry,\n" +
                "  c3 SDO_Topo_Geometry,\n" +
                "  c4 SYS.SDO_Topo_Geometry,\n" +
                "  c5 SDO_GeoRaster,\n" +
                "  c6 SYS.SDO_GeoRaster\n" +
                ");";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 SDO_Geometry,\n" +
                "\tc2 SYS.SDO_Geometry,\n" +
                "\tc3 SDO_Topo_Geometry,\n" +
                "\tc4 SYS.SDO_Topo_Geometry,\n" +
                "\tc5 SDO_GeoRaster,\n" +
                "\tc6 SYS.SDO_GeoRaster\n" +
                ");", format);
    }
}
