package com.gumihoy.sql.bvt.dialect.mysql.ddl.trigger.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-26.
 */
public class MySQLCreateTriggerTest_0 {

    @Test
    public void test_0() {
        String sql = "CREATE TRIGGER upd_check BEFORE UPDATE ON account\n" +
                "       FOR EACH ROW\n" +
                "       BEGIN\n" +
                "           IF NEW.amount < 0 THEN\n" +
                "               SET NEW.amount = 0;\n" +
                "           ELSEIF NEW.amount > 100 THEN\n" +
                "               SET NEW.amount = 100;\n" +
                "           END IF;\n" +
                "       END;";
        String format = SQLUtils.format(sql, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TRIGGER upd_check BEFORE UPDATE ON account\n" +
                "       FOR EACH ROW\n" +
                "       BEGIN\n" +
                "           IF NEW.amount < 0 THEN\n" +
                "               SET NEW.amount = 0;\n" +
                "           ELSEIF NEW.amount > 100 THEN\n" +
                "               SET NEW.amount = 100;\n" +
                "           END IF;\n" +
                "       END;", format);
    }


}
