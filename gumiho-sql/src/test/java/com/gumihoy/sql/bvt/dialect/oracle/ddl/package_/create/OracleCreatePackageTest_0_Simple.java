package com.gumihoy.sql.bvt.dialect.oracle.ddl.package_.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreatePackageTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE PACKAGE trans_data AUTHID DEFINER AS\n" +
                "  TYPE TimeRec IS RECORD (\n" +
                "    minutes SMALLINT,\n" +
                "    hours   SMALLINT);\n" +
                "  TYPE TransRec IS RECORD (\n" +
                "    category VARCHAR2(10),\n" +
                "    account  INT,\n" +
                "    amount   REAL,\n" +
                "    time_of  TimeRec);\n" +
                "  minimum_balance     CONSTANT REAL := 10.00;\n" +
                "  number_processed    INT;\n" +
                "  insufficient_funds  EXCEPTION;\n" +
                "  PRAGMA EXCEPTION_INIT(insufficient_funds, -4097);\n" +
                "END trans_data;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE PACKAGE trans_data\n" +
                "\tAUTHID DEFINER\n" +
                "AS\n" +
                "\tTYPE TimeRec IS RECORD (\n" +
                "\t\tminutes SMALLINT,\n" +
                "\t\thours SMALLINT\n" +
                "\t);\n" +
                "\tTYPE TransRec IS RECORD (\n" +
                "\t\tcategory VARCHAR2(10),\n" +
                "\t\taccount INT,\n" +
                "\t\tamount REAL,\n" +
                "\t\ttime_of TimeRec\n" +
                "\t);\n" +
                "\tminimum_balance CONSTANT REAL := 10.00;\n" +
                "\tnumber_processed INT;\n" +
                "\tinsufficient_funds EXCEPTION;\n" +
                "\tPRAGMA EXCEPTION_INIT (insufficient_funds, -4097);\n" +
                "END trans_data;", format);
    }


}
