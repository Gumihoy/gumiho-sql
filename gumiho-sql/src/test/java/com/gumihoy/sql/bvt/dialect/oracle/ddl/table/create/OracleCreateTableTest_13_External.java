package com.gumihoy.sql.bvt.dialect.oracle.ddl.table.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTableTest_13_External {

//    @Test
//    public void test_0() {
//        String sql = "CREATE TABLE dept_external (\n" +
//                "   deptno     NUMBER(6),\n" +
//                "   dname      VARCHAR2(20),\n" +
//                "   loc        VARCHAR2(25) \n" +
//                ")\n" +
//                "ORGANIZATION EXTERNAL\n" +
//                "(TYPE oracle_loader\n" +
//                " DEFAULT DIRECTORY admin\n" +
//                " ACCESS PARAMETERS\n" +
//                " (\n" +
//                "  RECORDS DELIMITED BY newline\n" +
//                "  BADFILE 'ulcase1.bad'\n" +
//                "  DISCARDFILE 'ulcase1.dis'\n" +
//                "  LOGFILE 'ulcase1.log'\n" +
//                "  SKIP 20\n" +
//                "  FIELDS TERMINATED BY \",\"  OPTIONALLY ENCLOSED BY '\"'\n" +
//                "  (\n" +
//                "   deptno     INTEGER EXTERNAL(6),\n" +
//                "   dname      CHAR(20),\n" +
//                "   loc        CHAR(25)\n" +
//                "  )\n" +
//                " )\n" +
//                " LOCATION ('ulcase1.ctl')\n" +
//                ")\n" +
//                "REJECT LIMIT UNLIMITED;";
//        System.out.println(sql);
//        String format = SQLUtils.format(sql, DBType.Oracle);
//        System.out.println("----------------");
//        System.out.println(format);
//        Assert.assertEquals("CREATE TABLE dept_external (\n" +
//                "   deptno     NUMBER(6),\n" +
//                "   dname      VARCHAR2(20),\n" +
//                "   loc        VARCHAR2(25) \n" +
//                ")\n" +
//                "ORGANIZATION EXTERNAL\n" +
//                "(TYPE oracle_loader\n" +
//                " DEFAULT DIRECTORY admin\n" +
//                " ACCESS PARAMETERS\n" +
//                " (\n" +
//                "  RECORDS DELIMITED BY newline\n" +
//                "  BADFILE 'ulcase1.bad'\n" +
//                "  DISCARDFILE 'ulcase1.dis'\n" +
//                "  LOGFILE 'ulcase1.log'\n" +
//                "  SKIP 20\n" +
//                "  FIELDS TERMINATED BY \",\"  OPTIONALLY ENCLOSED BY '\"'\n" +
//                "  (\n" +
//                "   deptno     INTEGER EXTERNAL(6),\n" +
//                "   dname      CHAR(20),\n" +
//                "   loc        CHAR(25)\n" +
//                "  )\n" +
//                " )\n" +
//                " LOCATION ('ulcase1.ctl')\n" +
//                ")\n" +
//                "REJECT LIMIT UNLIMITED;", format);
//    }


}
