package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLCreateTypeBodyStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.statement.SQLTypeBodyStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleTypeBodyStatementParser extends SQLTypeBodyStatementParser {

    public OracleTypeBodyStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateTypeBodyStatement parseCreate() {
        return super.parseCreate();
    }

    @Override
    public ISQLExpr parseCreateItem() {

        return null;
    }

}
