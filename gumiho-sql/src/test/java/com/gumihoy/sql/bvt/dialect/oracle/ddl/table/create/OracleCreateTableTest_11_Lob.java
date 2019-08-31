package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_11_Lob {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE print_media_new\n" +
                "    ( product_id        NUMBER(6)\n" +
                "    , ad_id             NUMBER(6)\n" +
                "    , ad_composite      BLOB\n" +
                "    , ad_sourcetext     CLOB\n" +
                "    , ad_finaltext      CLOB\n" +
                "    , ad_fltextn        NCLOB\n" +
                "    , ad_textdocs_ntab  textdoc_tab\n" +
                "    , ad_photo          BLOB\n" +
                "    , ad_graphic        BFILE\n" +
                "    , ad_header         adheader_typ\n" +
                "    ) NESTED TABLE ad_textdocs_ntab STORE AS textdocs_nestedtab_new\n" +
                "    LOB (ad_sourcetext, ad_finaltext) STORE AS\n" +
                "      (TABLESPACE example\n" +
                "       STORAGE (INITIAL 6144)\n" +
                "       CHUNK 4000\n" +
                "       NOCACHE LOGGING);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE print_media_new\n" +
                "    ( product_id        NUMBER(6)\n" +
                "    , ad_id             NUMBER(6)\n" +
                "    , ad_composite      BLOB\n" +
                "    , ad_sourcetext     CLOB\n" +
                "    , ad_finaltext      CLOB\n" +
                "    , ad_fltextn        NCLOB\n" +
                "    , ad_textdocs_ntab  textdoc_tab\n" +
                "    , ad_photo          BLOB\n" +
                "    , ad_graphic        BFILE\n" +
                "    , ad_header         adheader_typ\n" +
                "    ) NESTED TABLE ad_textdocs_ntab STORE AS textdocs_nestedtab_new\n" +
                "    LOB (ad_sourcetext, ad_finaltext) STORE AS\n" +
                "      (TABLESPACE example\n" +
                "       STORAGE (INITIAL 6144)\n" +
                "       CHUNK 4000\n" +
                "       NOCACHE LOGGING);", format);
    }


}
