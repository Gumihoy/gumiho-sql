package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_12_Index_Organized {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE countries_demo\n" +
                "    ( country_id      CHAR(2)\n" +
                "      CONSTRAINT country_id_nn_demo NOT NULL\n" +
                "    , country_name    VARCHAR2(40)\n" +
                "    , currency_name   VARCHAR2(25)\n" +
                "    , currency_symbol VARCHAR2(3)\n" +
                "    , region          VARCHAR2(15)\n" +
                "    , CONSTRAINT    country_c_id_pk_demo\n" +
                "                    PRIMARY KEY (country_id ) )\n" +
                "    ORGANIZATION INDEX \n" +
                "    INCLUDING   country_name \n" +
                "    PCTTHRESHOLD 2 \n" +
                "    STORAGE \n" +
                "     ( INITIAL  4K ) \n" +
                "   OVERFLOW \n" +
                "    STORAGE \n" +
                "      ( INITIAL  4K ); ";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE countries_demo\n" +
                "    ( country_id      CHAR(2)\n" +
                "      CONSTRAINT country_id_nn_demo NOT NULL\n" +
                "    , country_name    VARCHAR2(40)\n" +
                "    , currency_name   VARCHAR2(25)\n" +
                "    , currency_symbol VARCHAR2(3)\n" +
                "    , region          VARCHAR2(15)\n" +
                "    , CONSTRAINT    country_c_id_pk_demo\n" +
                "                    PRIMARY KEY (country_id ) )\n" +
                "    ORGANIZATION INDEX \n" +
                "    INCLUDING   country_name \n" +
                "    PCTTHRESHOLD 2 \n" +
                "    STORAGE \n" +
                "     ( INITIAL  4K ) \n" +
                "   OVERFLOW \n" +
                "    STORAGE \n" +
                "      ( INITIAL  4K );", format);
    }


}
