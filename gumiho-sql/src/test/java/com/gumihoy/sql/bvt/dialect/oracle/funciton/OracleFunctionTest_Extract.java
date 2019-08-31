package com.gumihoy.sql.bvt.dialect.oracle.funciton;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-18.
 */
public class OracleFunctionTest_Extract {


    @Test
    public void test_0() {
        String sql = "SELECT p1.snap_id, p1.dbid, p1.instance_number as instance_num, TO_CHAR(t1.BEGIN_INTERVAL_TIME, 'yyyy-MM-dd HH24:mi:ss') as BEGIN_INTERVAL_TIME, TO_CHAR(t1.END_INTERVAL_TIME, 'yyyy-MM-dd HH24:mi:ss') as END_INTERVAL_TIME, (f1. VALUE - p1. VALUE) /( extract( DAY FROM( end_interval_time - begin_interval_time)) * 24 * 60 * 60 + extract( HOUR FROM( end_interval_time - begin_interval_time)) * 60 * 60 + extract( MINUTE FROM( end_interval_time - begin_interval_time)) * 60 + extract( SECOND FROM( end_interval_time - begin_interval_time))) QPS, TO_CHAR(SYSDATE, 'yyyy-MM-dd HH24:mi:ss') as collection_time FROM dba_hist_sysstat p1, dba_hist_sysstat f1, dba_hist_snapshot t1 WHERE p1.snap_Id =(f1.snap_id - 1) AND p1.stat_name = 'parse count (total)' AND f1.stat_name = 'parse count (total)' AND p1.snap_id = t1.snap_id";;

        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);

        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT p1.snap_id, p1.dbid, p1.instance_number AS instance_num,\n" +
                "\tTO_CHAR(t1.BEGIN_INTERVAL_TIME, 'yyyy-MM-dd HH24:mi:ss') AS BEGIN_INTERVAL_TIME,\n" +
                "\tTO_CHAR(t1.END_INTERVAL_TIME, 'yyyy-MM-dd HH24:mi:ss') AS END_INTERVAL_TIME,\n" +
                "\t(f1.VALUE - p1.VALUE) / (EXTRACT(DAY FROM (end_interval_time - begin_interval_time)) * 24 * 60 * 60 + EXTRACT(HOUR FROM (end_interval_time - begin_interval_time)) * 60 * 60 + EXTRACT(MINUTE FROM (end_interval_time - begin_interval_time)) * 60 + EXTRACT(SECOND FROM (end_interval_time - begin_interval_time))) QPS,\n" +
                "\tTO_CHAR(SYSDATE, 'yyyy-MM-dd HH24:mi:ss') AS collection_time\n" +
                "FROM dba_hist_sysstat p1, dba_hist_sysstat f1, dba_hist_snapshot t1\n" +
                "WHERE p1.snap_Id = (f1.snap_id - 1)\n" +
                "\tAND p1.stat_name = 'parse count (total)'\n" +
                "\tAND f1.stat_name = 'parse count (total)'\n" +
                "\tAND p1.snap_id = t1.snap_id", format);
    }


    @Test
    public void test_1() {
        String sql = "SELECT warehouse_name,\n" +
                "       EXTRACT(warehouse_spec, '/Warehouse/Docks') \"Number of Docks\"\n" +
                "  FROM warehouses\n" +
                "  WHERE warehouse_spec IS NOT NULL\n" +
                "  ORDER BY warehouse_name;";

        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);

        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT warehouse_name, EXTRACT(warehouse_spec, '/Warehouse/Docks') \"Number of Docks\"\n" +
                "FROM warehouses\n" +
                "WHERE warehouse_spec IS NOT NULL\n" +
                "ORDER BY warehouse_name;", format);
    }

}
