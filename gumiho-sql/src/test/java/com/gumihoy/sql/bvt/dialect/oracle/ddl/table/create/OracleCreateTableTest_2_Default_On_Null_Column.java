package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_2_Default_On_Null_Column {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE myemp (employee_id number, last_name varchar2(25),\n" +
                "                    department_id NUMBER DEFAULT ON NULL 50 NOT NULL);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE myemp (\n" +
                "\temployee_id NUMBER,\n" +
                "\tlast_name VARCHAR2(25),\n" +
                "\tdepartment_id NUMBER DEFAULT ON NULL 50 NOT NULL\n" +
                ");", format);
    }


}
