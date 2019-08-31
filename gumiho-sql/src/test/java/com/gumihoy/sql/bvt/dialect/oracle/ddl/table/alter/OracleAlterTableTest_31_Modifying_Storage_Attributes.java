package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterTableTest_31_Modifying_Storage_Attributes {

    @Test
    public void test_0() {
        String sql = "ALTER TABLE JOBS_TEMP MOVE    \n" +
                "      STORAGE ( INITIAL 20K    \n" +
                "                NEXT 40K    \n" +
                "                MINEXTENTS 2    \n" +
                "                MAXEXTENTS 20    \n" +
                "                PCTINCREASE 0 )    \n" +
                "      TABLESPACE USERS;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER TABLE JOBS_TEMP MOVE    \n" +
                "      STORAGE ( INITIAL 20K    \n" +
                "                NEXT 40K    \n" +
                "                MINEXTENTS 2    \n" +
                "                MAXEXTENTS 20    \n" +
                "                PCTINCREASE 0 )    \n" +
                "      TABLESPACE USERS;", format);
    }

}
