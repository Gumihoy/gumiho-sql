package com.gumihoy.sql.bvt.dialect.postgresql.function;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectItem;
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectStatement;
import com.gumihoy.sql.dialect.postgresql.ast.element.select.PostgreSQLSelectQuery;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLParserUtils;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author kent on 2019-07-09.
 */
public class PostgreSQLNoArgumentFunctionTest {

    @Test
    public void test_0() {
        String sql = "SELECT CURRENT_DATE, CURRENT_TIMESTAMP,DBTIMEZONE, ITERATION_NUMBER, ITERATION_NUMBER, LOCALTIMESTAMP, " +
                "  ORA_INVOKING_USER, ORA_INVOKING_USERID, SESSIONTIMEZONE,  SYSDATE, SYSTIMESTAMP, UID,  USER, SQLERRM, SQLCODE\n" +
                "  FROM DUAL;";
        List<ISQLObject> stmts = SQLParserUtils.parseStatements(sql, DBType.PostgreSQL);

        Assert.assertEquals(1, stmts.size());

        Assert.assertTrue((stmts.get(0) instanceof SQLSelectStatement));
        Assert.assertTrue((((SQLSelectStatement) stmts.get(0)).getQuery() instanceof PostgreSQLSelectQuery));

        PostgreSQLSelectQuery selectQuery = (PostgreSQLSelectQuery) ((SQLSelectStatement) stmts.get(0)).getQuery();
        for (SQLSelectItem selectItem : selectQuery.getSelectItems()) {
            Assert.assertTrue((selectItem.getExpr() instanceof SQLMethodInvocation));
        }

        String format = SQLUtils.toSQLString(stmts, DBType.PostgreSQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT CURRENT_DATE, CURRENT_TIMESTAMP, DBTIMEZONE, ITERATION_NUMBER,\n" +
                "\tITERATION_NUMBER, LOCALTIMESTAMP, ORA_INVOKING_USER, ORA_INVOKING_USERID,\n" +
                "\tSESSIONTIMEZONE, SYSDATE, SYSTIMESTAMP, UID, USER, SQLERRM, SQLCODE\n" +
                "FROM DUAL;", format);
    }


}
