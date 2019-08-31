package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_14_Merging_Two_Table_Partitions {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE sales \n" +
                "   MERGE PARTITIONS sales_q4_2000, sales_q4_2000b\n" +
                "   INTO PARTITION sales_q4_2000;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE sales\n" +
                "\tMERGE PARTITIONS sales_q4_2000, sales_q4_2000b\n" +
                "\tINTO PARTITION sales_q4_2000;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE print_media_part \n" +
                "   MERGE PARTITIONS p2a, p2b INTO PARTITION p2ab TABLESPACE example\n" +
                "   NESTED TABLE ad_textdocs_ntab STORE AS nt_p2ab;\n";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media_part \n" +
                "   MERGE PARTITIONS p2a, p2b INTO PARTITION p2ab TABLESPACE example\n" +
                "   NESTED TABLE ad_textdocs_ntab STORE AS nt_p2ab;\n", format);
    }

}
