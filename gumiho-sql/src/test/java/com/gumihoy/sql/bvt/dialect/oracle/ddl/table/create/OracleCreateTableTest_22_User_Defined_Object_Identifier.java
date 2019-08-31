package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_22_User_Defined_Object_Identifier {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE employees_obj_t OF employees_typ (e_no PRIMARY KEY)\n" +
                "   OBJECT IDENTIFIER IS PRIMARY KEY;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE employees_obj_t OF employees_typ (\n" +
                "\te_no PRIMARY KEY\n" +
                ")\n" +
                "OBJECT IDENTIFIER IS PRIMARY KEY;", format);
    }


    @Test
    public void test_1() {
        String sql = "CREATE TABLE departments_t \n" +
                "   (d_no    NUMBER,\n" +
                "    mgr_ref REF employees_typ SCOPE IS employees_obj_t);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE departments_t (\n" +
                "\td_no NUMBER,\n" +
                "\tmgr_ref REF employees_typ SCOPE IS employees_obj_t\n" +
                ");", format);
    }

    @Test
    public void test_2() {
        String sql = "CREATE TABLE departments_t (\n" +
                "    d_no NUMBER,\n" +
                "    mgr_ref REF employees_typ \n" +
                "       CONSTRAINT mgr_in_emp REFERENCES employees_obj_t);";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE departments_t (\n" +
                "\td_no NUMBER,\n" +
                "\tmgr_ref REF employees_typ CONSTRAINT mgr_in_emp REFERENCES employees_obj_t\n" +
                ");", format);
    }

}
