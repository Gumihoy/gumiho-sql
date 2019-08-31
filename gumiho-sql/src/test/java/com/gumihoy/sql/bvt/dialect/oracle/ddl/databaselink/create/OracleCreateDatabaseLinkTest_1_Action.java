package com.gumihoy.sql.bvt.dialect.oracle.ddl.databaselink.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateDatabaseLinkTest_1_Action {

    @Test
    public void test_0() {
        String sql = "CREATE DATABASE LINK local \n" +
                "   CONNECT TO CURRENT_USER";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DATABASE LINK local\n" +
                "\tCONNECT TO CURRENT_USER", format);
    }


    @Test
    public void test_1() {
        String sql = "CREATE DATABASE LINK local \n" +
                "   CONNECT TO hr IDENTIFIED BY password";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DATABASE LINK local\n" +
                "\tCONNECT TO hr IDENTIFIED BY password", format);
    }

}
