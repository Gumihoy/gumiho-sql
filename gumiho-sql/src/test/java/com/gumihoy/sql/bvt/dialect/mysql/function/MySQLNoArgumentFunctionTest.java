package com.gumihoy.sql.bvt.dialect.mysql.function;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectItem;
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectStatement;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLSelectQuery;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLParserUtils;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author kent on 2019-06-30.
 */
public class MySQLNoArgumentFunctionTest {

    @Test
    public void test_0() {
        String sql = "SELECT CURRENT_DATE, CURRENT_TIME,CURRENT_TIMESTAMP, CURRENT_USER, LOCALTIME, LOCALTIMESTAMP, UTC_DATE, UTC_TIME, UTC_TIMESTAMP  from daul;";
        List<ISQLObject> stmts = SQLParserUtils.parseStatements(sql, DBType.MySQL);

        Assert.assertEquals(1, stmts.size());

        Assert.assertTrue((stmts.get(0) instanceof SQLSelectStatement));
        Assert.assertTrue((((SQLSelectStatement) stmts.get(0)).getQuery() instanceof MySQLSelectQuery));

        MySQLSelectQuery selectQuery = (MySQLSelectQuery) ((SQLSelectStatement) stmts.get(0)).getQuery();
        for (SQLSelectItem selectItem : selectQuery.getSelectItems()) {
            Assert.assertTrue((selectItem.getExpr() instanceof SQLMethodInvocation));
        }

        String format = SQLUtils.toSQLString(stmts, DBType.MySQL);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP, CURRENT_USER,\n" +
                "\tLOCALTIME, LOCALTIMESTAMP, UTC_DATE, UTC_TIME, UTC_TIMESTAMP\n" +
                "FROM daul;", format);
    }
}
