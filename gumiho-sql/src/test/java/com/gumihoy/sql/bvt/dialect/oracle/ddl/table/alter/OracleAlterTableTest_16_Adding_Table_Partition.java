package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_16_Adding_Table_Partition {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE print_media_part ADD PARTITION p3 VALUES LESS THAN (400)\n" +
                "  LOB(ad_photo, ad_composite) STORE AS (TABLESPACE omf_ts1)\n" +
                "  LOB(ad_sourcetext, ad_finaltext) STORE AS (TABLESPACE omf_ts2)\n" +
                "  NESTED TABLE ad_textdocs_ntab STORE AS nt_p3;\n";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_part ADD PARTITION p3 VALUES LESS THAN (400)\n" +
                "  LOB(ad_photo, ad_composite) STORE AS (TABLESPACE omf_ts1)\n" +
                "  LOB(ad_sourcetext, ad_finaltext) STORE AS (TABLESPACE omf_ts2)\n" +
                "  NESTED TABLE ad_textdocs_ntab STORE AS nt_p3;\n", format);
    }

}
