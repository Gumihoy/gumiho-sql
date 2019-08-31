package com.gumihoy.sql.bvt.dialect.oracle.ddl.database.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateDatabaseTest_1_CDB {

    @Test
    public void test_0() {
        String sql = "CREATE DATABASE newcdb\n" +
                "  USER SYS IDENTIFIED BY sys_password\n" +
                "  USER SYSTEM IDENTIFIED BY system_password\n" +
                "  LOGFILE GROUP 1 ('/u01/logs/my/redo01a.log','/u02/logs/my/redo01b.log')\n" +
                "             SIZE 100M BLOCKSIZE 512,\n" +
                "          GROUP 2 ('/u01/logs/my/redo02a.log','/u02/logs/my/redo02b.log')\n" +
                "             SIZE 100M BLOCKSIZE 512,\n" +
                "          GROUP 3 ('/u01/logs/my/redo03a.log','/u02/logs/my/redo03b.log')\n" +
                "             SIZE 100M BLOCKSIZE 512\n" +
                "  MAXLOGHISTORY 1\n" +
                "  MAXLOGFILES 16\n" +
                "  MAXLOGMEMBERS 3\n" +
                "  MAXDATAFILES 1024\n" +
                "  CHARACTER SET AL32UTF8\n" +
                "  NATIONAL CHARACTER SET AL16UTF16\n" +
                "  EXTENT MANAGEMENT LOCAL\n" +
                "  DATAFILE '/u01/app/oracle/oradata/newcdb/system01.dbf'\n" +
                "    SIZE 700M REUSE AUTOEXTEND ON NEXT 10240K MAXSIZE UNLIMITED\n" +
                "  SYSAUX DATAFILE '/u01/app/oracle/oradata/newcdb/sysaux01.dbf'\n" +
                "    SIZE 550M REUSE AUTOEXTEND ON NEXT 10240K MAXSIZE UNLIMITED\n" +
                "  DEFAULT TABLESPACE deftbs\n" +
                "    DATAFILE '/u01/app/oracle/oradata/newcdb/deftbs01.dbf'\n" +
                "    SIZE 500M REUSE AUTOEXTEND ON MAXSIZE UNLIMITED\n" +
                "  DEFAULT TEMPORARY TABLESPACE tempts1\n" +
                "    TEMPFILE '/u01/app/oracle/oradata/newcdb/temp01.dbf'\n" +
                "    SIZE 20M REUSE AUTOEXTEND ON NEXT 640K MAXSIZE UNLIMITED,\n" +
                "     '/u01/app/oracle/oradata/newcdb/temp01.dbf'\n" +
                "    SIZE 20M REUSE AUTOEXTEND ON NEXT 640K MAXSIZE UNLIMITED\n" +
                "  UNDO TABLESPACE undotbs1\n" +
                "    DATAFILE '/u01/app/oracle/oradata/newcdb/undotbs01.dbf'\n" +
                "    SIZE 200M REUSE AUTOEXTEND ON NEXT 5120K MAXSIZE UNLIMITED\n" +
                "  ENABLE PLUGGABLE DATABASE\n" +
                "    SEED\n" +
                "    FILE_NAME_CONVERT = ('/u01/app/oracle/oradata/newcdb/',\n" +
                "                         '/u01/app/oracle/oradata/pdbseed/')\n" +
                "    SYSTEM DATAFILES SIZE 125M AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED\n" +
                "    SYSAUX DATAFILES SIZE 100M\n" +
                "  USER_DATA TABLESPACE usertbs\n" +
                "    DATAFILE '/u01/app/oracle/oradata/pdbseed/usertbs01.dbf'\n" +
                "    SIZE 200M REUSE AUTOEXTEND ON MAXSIZE UNLIMITED;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE DATABASE newcdb\n" +
                "\tUSER SYS IDENTIFIED BY sys_password\n" +
                "\tUSER SYSTEM IDENTIFIED BY system_password\n" +
                "\tLOGFILE\n" +
                "\t\tGROUP 1 ('/u01/logs/my/redo01a.log', '/u02/logs/my/redo01b.log') SIZE 100M BLOCKSIZE 512,\n" +
                "\t\tGROUP 2 ('/u01/logs/my/redo02a.log', '/u02/logs/my/redo02b.log') SIZE 100M BLOCKSIZE 512,\n" +
                "\t\tGROUP 3 ('/u01/logs/my/redo03a.log', '/u02/logs/my/redo03b.log') SIZE 100M BLOCKSIZE 512\n" +
                "\tMAXLOGHISTORY 1\n" +
                "\tMAXLOGFILES 16\n" +
                "\tMAXLOGMEMBERS 3\n" +
                "\tMAXDATAFILES 1024\n" +
                "\tCHARACTER SET AL32UTF8\n" +
                "\tNATIONAL CHARACTER SET AL16UTF16\n" +
                "\tEXTENT MANAGEMENT LOCAL\n" +
                "\tDATAFILE '/u01/app/oracle/oradata/newcdb/system01.dbf' SIZE 700M REUSE AUTOEXTEND ON NEXT 10240K MAXSIZE UNLIMITED\n" +
                "\tSYSAUX DATAFILE '/u01/app/oracle/oradata/newcdb/sysaux01.dbf' SIZE 550M REUSE AUTOEXTEND ON NEXT 10240K MAXSIZE UNLIMITED\n" +
                "\tDEFAULT TABLESPACE deftbs DATAFILE '/u01/app/oracle/oradata/newcdb/deftbs01.dbf' SIZE 500M REUSE AUTOEXTEND ON MAXSIZE UNLIMITED\n" +
                "\tDEFAULT TEMPORARY TABLESPACE tempts1\n" +
                "\t\tTEMPFILE\n" +
                "\t\t\t'/u01/app/oracle/oradata/newcdb/temp01.dbf' SIZE 20M REUSE AUTOEXTEND ON NEXT 640K MAXSIZE UNLIMITED,\n" +
                "\t\t\t'/u01/app/oracle/oradata/newcdb/temp01.dbf' SIZE 20M REUSE AUTOEXTEND ON NEXT 640K MAXSIZE UNLIMITED\n" +
                "\tUNDO TABLESPACE undotbs1\n" +
                "\t\tDATAFILE '/u01/app/oracle/oradata/newcdb/undotbs01.dbf' SIZE 200M REUSE AUTOEXTEND ON NEXT 5120K MAXSIZE UNLIMITED\n" +
                "\tENABLE PLUGGABLE DATABASE\n" +
                "\t\tSEED\n" +
                "\t\tFILE_NAME_CONVERT = (\n" +
                "\t\t\t'/u01/app/oracle/oradata/newcdb/', '/u01/app/oracle/oradata/pdbseed/'\n" +
                "\t\t)\n" +
                "\t\tSYSTEM DATAFILES SIZE 125M AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED\n" +
                "\t\tSYSAUX DATAFILES SIZE 100M\n" +
                "\tUSER_DATA TABLESPACE usertbs\n" +
                "\t\tDATAFILE '/u01/app/oracle/oradata/pdbseed/usertbs01.dbf' SIZE 200M REUSE AUTOEXTEND ON MAXSIZE UNLIMITED;", format);
    }


}
