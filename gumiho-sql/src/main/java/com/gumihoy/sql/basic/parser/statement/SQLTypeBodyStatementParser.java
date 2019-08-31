package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLAlterTypeBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLCreateTypeBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLDropTypeBodyStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLTypeBodyStatementParser extends AbstractSQLStatementParser {


    public SQLTypeBodyStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateTypeBodyStatement parseCreate() {
        SQLCreateTypeBodyStatement x = new SQLCreateTypeBodyStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        x.setOrReplace(exprParser.parseOrReplace());

        acceptAndNextToken(SQLToken.TokenKind.TYPE, true);
        acceptAndNextToken(SQLToken.TokenKind.BODY, true);

        x.setName(exprParser.parseName());

        SQLASType as = exprParser.parseAsType(true);
        x.setAs(as);

        for (;;) {
            ISQLExpr item = parseCreateItem();
            x.addItem(item);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.END, true);

        return x;
    }

    public ISQLExpr parseCreateItem() {
        return null;
    }


    public SQLAlterTypeBodyStatement parseAlter() {
        throw new UnsupportedOperationException();
    }


    public SQLDropTypeBodyStatement parseDrop() {
        SQLDropTypeBodyStatement x = new SQLDropTypeBodyStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TYPE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.BODY, true);

        x.setName(exprParser.parseName());
        return x;
    }

}
