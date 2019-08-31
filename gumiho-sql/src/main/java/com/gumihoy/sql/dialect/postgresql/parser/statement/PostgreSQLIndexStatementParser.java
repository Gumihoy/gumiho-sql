package com.gumihoy.sql.dialect.postgresql.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLIndexStatementParser;
import com.gumihoy.sql.dialect.postgresql.parser.PostgreSQLExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class PostgreSQLIndexStatementParser extends SQLIndexStatementParser {

    public PostgreSQLIndexStatementParser(PostgreSQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateIndexStatement parseCreate() {
        SQLCreateIndexStatement x = new SQLCreateIndexStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        acceptAndNextToken(SQLToken.TokenKind.TABLE, true);

        x.setName(exprParser.parseName());

        return x;
    }


    public SQLAlterIndexStatement parseAlter() {
        SQLAlterIndexStatement x = new SQLAlterIndexStatement(this.dbType);
        return x;
    }




    public SQLDropIndexStatement parseDrop() {
        SQLDropIndexStatement x = new SQLDropIndexStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.INDEX, true);

        x.setConcurrently(this.acceptAndNextToken(SQLToken.TokenKind.CONCURRENTLY));
        x.setIfExists(exprParser.parseIfExists());

        for (;;) {
            x.addName(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        x.setDropBehavior(exprParser.parseDropBehavior());
        return x;
    }

}
