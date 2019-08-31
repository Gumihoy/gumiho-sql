package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_10_Multilevel_Collection {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE business_contacts (\n" +
                "   company_name VARCHAR2(25),\n" +
                "   company_reps customer_list)\n" +
                "   NESTED TABLE company_reps STORE AS outer_ntab\n" +
                "   (NESTED TABLE phones STORE AS inner_ntab);";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE business_contacts (\n" +
                "   company_name VARCHAR2(25),\n" +
                "   company_reps customer_list)\n" +
                "   NESTED TABLE company_reps STORE AS outer_ntab\n" +
                "   (NESTED TABLE phones STORE AS inner_ntab);", format);
    }


}
