package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_21_Object {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE departments_obj_t OF department_typ; ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE departments_obj_t OF department_typ;", format);
    }

}
