package com.gumihoy.sql.bvt.dialect.oracle.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTriggerTest_12_Derives_New_Column_Values {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TRIGGER Derived \n" +
                "BEFORE INSERT OR UPDATE OF Ename ON Emp\n" +
                "\n" +
                "/* Before updating the ENAME field, derive the values for\n" +
                "   the UPPERNAME and SOUNDEXNAME fields. Restrict users\n" +
                "   from updating these fields directly: */\n" +
                "FOR EACH ROW\n" +
                "BEGIN\n" +
                "  :NEW.Uppername := UPPER(:NEW.Ename);\n" +
                "  :NEW.Soundexname := SOUNDEX(:NEW.Ename);\n" +
                "END;\n" +
                "/";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TRIGGER Derived\n" +
                "\tBEFORE\n" +
                "\t\tINSERT OR\n" +
                "\t\tUPDATE OF Ename\n" +
                "\tON Emp\n" +
                "\tBEGIN\n" +
                "\t\t:NEW.Uppername := UPPER(:NEW.Ename);\n" +
                "\t\t:NEW.Soundexname := SOUNDEX(:NEW.Ename);\n" +
                "\tEND;", format);
    }


}
