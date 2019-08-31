package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_33_Data_Encryption {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE employees\n" +
                "   MODIFY (salary ENCRYPT USING 'AES256' 'NOMAC');";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees\n" +
                "   MODIFY (salary ENCRYPT USING 'AES256' 'NOMAC');", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER TABLE customers\n" +
                "   ADD (online_acct_pw VARCHAR2(8) ENCRYPT 'NOMAC' NO SALT);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE customers\n" +
                "   ADD (online_acct_pw VARCHAR2(8) ENCRYPT 'NOMAC' NO SALT);", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER TABLE customers\n" +
                "   MODIFY (online_acct_pw DECRYPT);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE customers\n" +
                "   MODIFY (online_acct_pw DECRYPT);", format);
    }

}
