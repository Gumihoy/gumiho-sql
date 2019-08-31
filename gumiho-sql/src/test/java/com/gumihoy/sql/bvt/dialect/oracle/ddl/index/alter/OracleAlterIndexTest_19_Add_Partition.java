package com.gumihoy.sql.bvt.dialect.oracle.ddl.index.alter;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleAlterIndexTest_19_Add_Partition {

    @Test
    public void test_0() {
        String sql = "ALTER index JOBS_Temp ADD PARTITION chk_sal_min TABLESPACE t NOCOMPRESS;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("ALTER INDEX JOBS_Temp\n" +
                "\tADD PARTITION chk_sal_min TABLESPACE t NOCOMPRESS;", format);
    }


}
