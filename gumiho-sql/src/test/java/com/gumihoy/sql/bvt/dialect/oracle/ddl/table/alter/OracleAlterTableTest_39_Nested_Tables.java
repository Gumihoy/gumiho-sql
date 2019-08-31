package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_39_Nested_Tables {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE employees ADD (skills skill_table_type)\n" +
                "    NESTED TABLE skills STORE AS nested_skill_table;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE employees ADD (skills skill_table_type)\n" +
                "    NESTED TABLE skills STORE AS nested_skill_table;", format);
    }


}
