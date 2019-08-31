package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.column;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleColumnConstraint_2_Not_Null {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE locations_demo\n" +
                "   MODIFY (country_id CONSTRAINT country_nn NOT NULL);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE locations_demo\n" +
                "   MODIFY (country_id CONSTRAINT country_nn NOT NULL);", format);
    }

}
