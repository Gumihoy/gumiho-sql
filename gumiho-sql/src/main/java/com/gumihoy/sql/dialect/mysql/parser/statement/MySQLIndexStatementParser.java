package com.gumihoy.sql.dialect.mysql.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLIndexStatementParser;
import com.gumihoy.sql.dialect.mysql.parser.MySQLExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class MySQLIndexStatementParser extends SQLIndexStatementParser {

    public MySQLIndexStatementParser(MySQLExprParser exprParser) {
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

        x.addName(exprParser.parseExpr());

        this.acceptAndNextToken(SQLToken.TokenKind.ON, true);
        x.setTable(exprParser.parseName());

        for (;;) {
            ISQLExpr option = parseDropOption();
            if (option == null) {
                break;
            }
            x.addOption(option);
        }

        return x;
    }

    public ISQLExpr parseDropOption() {
        return null;
    }


}
