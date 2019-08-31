package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_35_Default_Column_Value {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE product_information\n" +
                "  MODIFY (min_price DEFAULT 10);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE product_information\n" +
                "  MODIFY (min_price DEFAULT 10);", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE product_information\n" +
                "   MODIFY (min_price DEFAULT NULL);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE product_information\n" +
                "   MODIFY (min_price DEFAULT NULL);", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE t1 ADD (id NUMBER DEFAULT ON NULL s1.NEXTVAL NOT NULL);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE t1 ADD (id NUMBER DEFAULT ON NULL s1.NEXTVAL NOT NULL);", format);
    }

}
