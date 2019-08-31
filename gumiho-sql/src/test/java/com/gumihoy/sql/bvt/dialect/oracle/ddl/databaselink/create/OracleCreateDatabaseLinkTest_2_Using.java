package com.gumihoy.sql.bvt.dialect.oracle.ddl.databaselink.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateDatabaseLinkTest_2_Using {

    @Test
    public void test_0() {
        String sql = "CREATE DATABASE LINK local \n" +
                "   CONNECT TO hr IDENTIFIED BY password\n" +
                "   USING 'local';";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DATABASE LINK local\n" +
                "\tCONNECT TO hr IDENTIFIED BY password\n" +
                "\tUSING 'local';", format);
    }


}
