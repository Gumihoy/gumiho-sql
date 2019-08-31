package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_1_Collection_Retrieval {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE print_media MODIFY NESTED TABLE ad_textdocs_ntab\n" +
                "   RETURN AS VALUE; ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE print_media MODIFY NESTED TABLE ad_textdocs_ntab\n" +
                "   RETURN AS VALUE; ", format);
    }


}
