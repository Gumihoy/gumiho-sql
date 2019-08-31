package com.gumihoy.sql.bvt.dialect.oracle.ddl.databaselink.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterDatabaseLinkTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "ALTER DATABASE LINK private_link \n" +
                "  CONNECT TO hr IDENTIFIED BY hr_new_password;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER DATABASE LINK private_link\n" +
                "\tCONNECT TO hr IDENTIFIED BY hr_new_password;", format);
    }

    @Test
    public void test_1() {
        String sql = "ALTER PUBLIC DATABASE LINK public_link\n" +
                "  CONNECT TO scott IDENTIFIED BY scott_new_password;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER PUBLIC DATABASE LINK public_link\n" +
                "\tCONNECT TO scott IDENTIFIED BY scott_new_password;", format);
    }

    @Test
    public void test_2() {
        String sql = "ALTER SHARED PUBLIC DATABASE LINK shared_pub_link\n" +
                "  CONNECT TO scott IDENTIFIED BY scott_new_password\n" +
                "  AUTHENTICATED BY hr IDENTIFIED BY hr_new_password; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER SHARED PUBLIC DATABASE LINK shared_pub_link\n" +
                "\tCONNECT TO scott IDENTIFIED BY scott_new_password\n" +
                "\tAUTHENTICATED BY hr IDENTIFIED BY hr_new_password;", format);
    }


    @Test
    public void test_3() {
        String sql = "ALTER SHARED DATABASE LINK shared_pub_link\n" +
                "  CONNECT TO scott IDENTIFIED BY scott_new_password;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER SHARED DATABASE LINK shared_pub_link\n" +
                "\tCONNECT TO scott IDENTIFIED BY scott_new_password;", format);
    }


}
