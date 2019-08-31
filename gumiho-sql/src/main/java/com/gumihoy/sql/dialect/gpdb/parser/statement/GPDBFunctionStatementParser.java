package com.gumihoy.sql.dialect.gpdb.parser.statement;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLAlterFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLCreateFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLDropFunctionStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLFunctionStatementParser;
import com.gumihoy.sql.dialect.gpdb.parser.GPDBExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class GPDBFunctionStatementParser extends SQLFunctionStatementParser {

    public GPDBFunctionStatementParser(GPDBExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateFunctionStatement parseCreate() {

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.FUNCTION, true);

        SQLCreateFunctionStatement x = new SQLCreateFunctionStatement(this.dbType);

        x.setName(exprParser.parseName());

        return x;
    }




    public SQLAlterFunctionStatement parseAlter() {
        SQLAlterFunctionStatement x = new SQLAlterFunctionStatement(this.dbType);
        return x;
    }


    public SQLDropFunctionStatement parseDrop() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION, true);

        SQLDropFunctionStatement x = new SQLDropFunctionStatement(this.dbType);

        boolean ifExists = exprParser.parseIfExists();
        x.setIfExists(ifExists);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }

}
