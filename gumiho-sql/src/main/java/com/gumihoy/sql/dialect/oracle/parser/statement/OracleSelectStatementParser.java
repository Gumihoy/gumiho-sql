package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.statement.SQLSelectStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-13.
 */
public class OracleSelectStatementParser extends SQLSelectStatementParser {


    public OracleSelectStatementParser(String sql) {
        super(new OracleExprParser(sql));
    }

    public OracleSelectStatementParser(SQLLexer lexer) {
        super(new OracleExprParser(lexer));
    }

    public OracleSelectStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }

    {
        dbType = DBType.Oracle;
    }

    @Override
    public SQLSelectStatement parser() {
        return super.parser();
    }
}
