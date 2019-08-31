package com.gumihoy.sql.bvt.dialect.oracle.ddl.typebody.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreateTypeBodyTest_0_Member_Function {

    @Test
    public void test_0() {
        String sql = "CREATE OR REPLACE TYPE BODY data_typ1 IS \n" +
                "  MEMBER FUNCTION prod (invent NUMBER) RETURN NUMBER IS \n" +
                "  BEGIN \n" +
                "  RETURN (year + invent); \n" +
                "  END; \n" +
                "     MEMBER FUNCTION qtr(der_qtr DATE) RETURN CHAR IS \n" +
                "     BEGIN \n" +
                "       IF (der_qtr < TO_DATE('01-APR', 'DD-MON')) THEN \n" +
                "         RETURN 'FIRST'; \n" +
                "       ELSIF (der_qtr < TO_DATE('01-JUL', 'DD-MON')) THEN \n" +
                "         RETURN 'SECOND'; \n" +
                "       ELSIF (der_qtr < TO_DATE('01-OCT', 'DD-MON')) THEN \n" +
                "         RETURN 'THIRD'; \n" +
                "       ELSE \n" +
                "         RETURN 'FOURTH'; \n" +
                "       END IF; \n" +
                "     END; \n" +
                "   END;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE TYPE BODY data_typ1 IS \n" +
                "  MEMBER FUNCTION prod (invent NUMBER) RETURN NUMBER IS \n" +
                "  BEGIN \n" +
                "  RETURN (year + invent); \n" +
                "  END; \n" +
                "     MEMBER FUNCTION qtr(der_qtr DATE) RETURN CHAR IS \n" +
                "     BEGIN \n" +
                "       IF (der_qtr < TO_DATE('01-APR', 'DD-MON')) THEN \n" +
                "         RETURN 'FIRST'; \n" +
                "       ELSIF (der_qtr < TO_DATE('01-JUL', 'DD-MON')) THEN \n" +
                "         RETURN 'SECOND'; \n" +
                "       ELSIF (der_qtr < TO_DATE('01-OCT', 'DD-MON')) THEN \n" +
                "         RETURN 'THIRD'; \n" +
                "       ELSE \n" +
                "         RETURN 'FOURTH'; \n" +
                "       END IF; \n" +
                "     END; \n" +
                "   END;", format);
    }


}
