package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_13_Splitting_Table_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE sales SPLIT PARTITION SALES_Q4_2000 \n" +
                "   AT (TO_DATE('15-NOV-2000','DD-MON-YYYY'))\n" +
                "   INTO (PARTITION SALES_Q4_2000, PARTITION SALES_Q4_2000b);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales SPLIT PARTITION SALES_Q4_2000 \n" +
                "   AT (TO_DATE('15-NOV-2000','DD-MON-YYYY'))\n" +
                "   INTO (PARTITION SALES_Q4_2000, PARTITION SALES_Q4_2000b);", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE sales SPLIT PARTITION SALES_Q1_2002 INTO (\n" +
                " PARTITION SALES_JAN_2002 VALUES LESS THAN (TO_DATE('01-FEB-2002','DD-MON-YYYY')),\n" +
                " PARTITION SALES_FEB_2002 VALUES LESS THAN (TO_DATE('01-MAR-2002','DD-MON-YYYY')),\n" +
                " PARTITION SALES_MAR_2002);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales SPLIT PARTITION SALES_Q1_2002 INTO (\n" +
                " PARTITION SALES_JAN_2002 VALUES LESS THAN (TO_DATE('01-FEB-2002','DD-MON-YYYY')),\n" +
                " PARTITION SALES_FEB_2002 VALUES LESS THAN (TO_DATE('01-MAR-2002','DD-MON-YYYY')),\n" +
                " PARTITION SALES_MAR_2002);", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE print_media_part\n" +
                "   SPLIT PARTITION p2 AT (150) INTO\n" +
                "   (PARTITION p2a TABLESPACE omf_ts1\n" +
                "      LOB (ad_photo, ad_composite) STORE AS (TABLESPACE omf_ts2),\n" +
                "   PARTITION p2b \n" +
                "      LOB (ad_photo, ad_composite) STORE AS (TABLESPACE omf_ts2))\n" +
                "   NESTED TABLE ad_textdocs_ntab INTO (PARTITION nt_p2a, PARTITION nt_p2b);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_part\n" +
                "   SPLIT PARTITION p2 AT (150) INTO\n" +
                "   (PARTITION p2a TABLESPACE omf_ts1\n" +
                "      LOB (ad_photo, ad_composite) STORE AS (TABLESPACE omf_ts2),\n" +
                "   PARTITION p2b \n" +
                "      LOB (ad_photo, ad_composite) STORE AS (TABLESPACE omf_ts2))\n" +
                "   NESTED TABLE ad_textdocs_ntab INTO (PARTITION nt_p2a, PARTITION nt_p2b);", format);
    }

}
