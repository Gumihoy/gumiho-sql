package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_27_Object_Identifiers {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE dept ADD CONSTRAINT mgr_cons FOREIGN KEY (mgr_ref)\n" +
                "   REFERENCES emp;\n" +
                "ALTER TABLE dept ADD sr_mgr REF emp_t REFERENCES emp;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE dept ADD CONSTRAINT mgr_cons FOREIGN KEY (mgr_ref)\n" +
                "   REFERENCES emp;\n" +
                "ALTER TABLE dept ADD sr_mgr REF emp_t REFERENCES emp;", format);
    }

}
