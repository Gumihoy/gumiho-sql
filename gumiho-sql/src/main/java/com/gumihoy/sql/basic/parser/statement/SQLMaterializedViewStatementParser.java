package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLAlterMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLCreateMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLDropMaterializedViewStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLMaterializedViewStatementParser extends AbstractSQLStatementParser {


    public SQLMaterializedViewStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateMaterializedViewStatement parseCreate() {
        SQLCreateMaterializedViewStatement x = new SQLCreateMaterializedViewStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        acceptAndNextToken(SQLToken.TokenKind.TABLE, true);
        if (lexer.token().kind == SQLToken.TokenKind.IF) {
            nextToken();
            acceptAndNextToken(SQLToken.TokenKind.NOT, true);
            acceptAndNextToken(SQLToken.TokenKind.EXISTS, true);
            x.setIfNotExists(true);
        }

        x.setName(exprParser.parseName());

        return x;
    }




    public SQLAlterMaterializedViewStatement parseAlter() {
        SQLAlterMaterializedViewStatement x = new SQLAlterMaterializedViewStatement(this.dbType);
        return x;
    }




    public SQLDropMaterializedViewStatement parseDrop() {
        SQLDropMaterializedViewStatement x = new SQLDropMaterializedViewStatement(this.dbType);
        return x;
    }

}
