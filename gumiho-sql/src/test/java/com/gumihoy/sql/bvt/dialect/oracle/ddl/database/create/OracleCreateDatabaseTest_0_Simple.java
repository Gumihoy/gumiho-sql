package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateDatabaseTest_0_Simple {

    @Test
    public void test_0() {
        String sql = "CREATE DATABASE sample\n" +
                "   CONTROLFILE REUSE \n" +
                "   LOGFILE\n" +
                "      GROUP 1 ('diskx:log1.log', 'disky:log1.log') SIZE 50K, \n" +
                "      GROUP 2 ('diskx:log2.log', 'disky:log2.log') SIZE 50K \n" +
                "   MAXLOGFILES 5 \n" +
                "   MAXLOGHISTORY 100 \n" +
                "   MAXDATAFILES 10 \n" +
                "   MAXINSTANCES 2 \n" +
                "   ARCHIVELOG \n" +
                "   CHARACTER SET AL32UTF8\n" +
                "   NATIONAL CHARACTER SET AL16UTF16\n" +
                "   DATAFILE  \n" +
                "      'disk1:df1.dbf' AUTOEXTEND ON,\n" +
                "      'disk2:df2.dbf' AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED\n" +
                "   DEFAULT TEMPORARY TABLESPACE temp_ts\n" +
                "   UNDO TABLESPACE undo_ts \n" +
                "   SET TIME_ZONE = '+02:00';";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DATABASE sample\n" +
                "\tCONTROLFILE REUSE\n" +
                "\tLOGFILE\n" +
                "\t\tGROUP 1 ('diskx:log1.log', 'disky:log1.log') SIZE 50K,\n" +
                "\t\tGROUP 2 ('diskx:log2.log', 'disky:log2.log') SIZE 50K\n" +
                "\tMAXLOGFILES 5\n" +
                "\tMAXLOGHISTORY 100\n" +
                "\tMAXDATAFILES 10\n" +
                "\tMAXINSTANCES 2\n" +
                "\tARCHIVELOG\n" +
                "\tCHARACTER SET AL32UTF8\n" +
                "\tNATIONAL CHARACTER SET AL16UTF16\n" +
                "\tDATAFILE 'disk1:df1.dbf' AUTOEXTEND ON,\n" +
                "\t'disk2:df2.dbf' AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED\n" +
                "\tDEFAULT TEMPORARY TABLESPACE temp_ts\n" +
                "\tUNDO TABLESPACE undo_ts\n" +
                "\tSET TIME_ZONE = '+02:00';", format);
    }


}
