package com.gumihoy.sql.bvt.dialect.oracle.ddl.index.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterIndexTest_18_modify_index_default {

    @Test
    public void test_0() {
        String sql = "ALTER INDEX JOBS_Temp MODIFY DEFAULT ATTRIBUTES FOR PARTITION partitionName TABLESPACE DEFAULT";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX JOBS_Temp\n" +
                "\tMODIFY DEFAULT ATTRIBUTES FOR PARTITION partitionName\n" +
                "\t\tTABLESPACE DEFAULT", format);
    }

}
