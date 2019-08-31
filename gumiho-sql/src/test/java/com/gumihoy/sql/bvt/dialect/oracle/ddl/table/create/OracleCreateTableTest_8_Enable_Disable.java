package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_8_Enable_Disable {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE departments_demo\n" +
                "    ( department_id    NUMBER(4)   PRIMARY KEY DISABLE\n" +
                "    , department_name  VARCHAR2(30)\n" +
                "           CONSTRAINT  dept_name_nn  NOT NULL\n" +
                "    , manager_id       NUMBER(6)\n" +
                "    , location_id      NUMBER(4)\n" +
                "    , dn               VARCHAR2(300)\n" +
                "    ) ;\n";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE departments_demo\n" +
                "    ( department_id    NUMBER(4)   PRIMARY KEY DISABLE\n" +
                "    , department_name  VARCHAR2(30)\n" +
                "           CONSTRAINT  dept_name_nn  NOT NULL\n" +
                "    , manager_id       NUMBER(6)\n" +
                "    , location_id      NUMBER(4)\n" +
                "    , dn               VARCHAR2(300)\n" +
                "    ) ;\n", format);
    }


}
