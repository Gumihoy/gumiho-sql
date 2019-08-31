package com.gumihoy.sql.dialect.postgresql.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLAlterFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLCreateFunctionStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLFunctionStatementParser;

/**
 * @author kent on 2019-06-25.
 */
public class PostgreSQLFunctionStatementParser extends SQLFunctionStatementParser {


    public PostgreSQLFunctionStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateFunctionStatement parseCreate() {

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.FUNCTION, true);

        SQLCreateFunctionStatement x = new SQLCreateFunctionStatement(this.dbType);

        x.setName(exprParser.parseName());

        return x;
    }


    @Override
    public SQLAlterFunctionStatement parseAlter() {
        SQLAlterFunctionStatement x = new SQLAlterFunctionStatement(this.dbType);
        return x;
    }




}
