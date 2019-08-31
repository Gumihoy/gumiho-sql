package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.table;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleTableConstraint_0_Unique_Key {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE promotions_var2\n" +
                "    ( promo_id         NUMBER(6)\n" +
                "    , promo_name       VARCHAR2(20)\n" +
                "    , promo_category   VARCHAR2(15)\n" +
                "    , promo_cost       NUMBER(10,2)\n" +
                "    , promo_begin_date DATE\n" +
                "    , promo_end_date   DATE\n" +
                "    , CONSTRAINT promo_id_u UNIQUE (promo_id)\n" +
                "   USING INDEX PCTFREE 20\n" +
                "      TABLESPACE stocks\n" +
                "      STORAGE (INITIAL 8M) ); ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE promotions_var2\n" +
                "    ( promo_id         NUMBER(6)\n" +
                "    , promo_name       VARCHAR2(20)\n" +
                "    , promo_category   VARCHAR2(15)\n" +
                "    , promo_cost       NUMBER(10,2)\n" +
                "    , promo_begin_date DATE\n" +
                "    , promo_end_date   DATE\n" +
                "    , CONSTRAINT promo_id_u UNIQUE (promo_id)\n" +
                "   USING INDEX PCTFREE 20\n" +
                "      TABLESPACE stocks\n" +
                "      STORAGE (INITIAL 8M) ); ", format);
    }


    @Test
    public void test_1() {
        String sql = "ALTER TABLE warehouses\n" +
                "   ADD CONSTRAINT wh_unq UNIQUE (warehouse_id, warehouse_name)\n" +
                "   USING INDEX PCTFREE 5\n" +
                "   EXCEPTIONS INTO wrong_id;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE warehouses\n" +
                "   ADD CONSTRAINT wh_unq UNIQUE (warehouse_id, warehouse_name)\n" +
                "   USING INDEX PCTFREE 5\n" +
                "   EXCEPTIONS INTO wrong_id;", format);
    }


}
