package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.common.SQLCollateOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.type.alter.ISQLAlterTypeAction;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLAlterTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLCreateTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLDropTypeStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLTypeStatementParser extends AbstractSQLStatementParser {


    public SQLTypeStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateTypeStatement parseCreate() {
        SQLCreateTypeStatement x = new SQLCreateTypeStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        boolean orReplace = exprParser.parseOrReplace();
        x.setOrReplace(orReplace);

        acceptAndNextToken(SQLToken.TokenKind.TYPE, true);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        boolean force = this.acceptAndNextToken(SQLToken.TokenKind.FORCE);
        x.setForce(force);

        if (this.acceptAndNextToken(SQLToken.TokenKind.OID)) {
            x.setOidLiteral(exprParser.parseExpr());
        }

        SQLSharingClause sharingClause = exprParser.parseSharingClause();
        x.setSharingClause(sharingClause);

        SQLCollateOptionExpr collateOptionExpr = exprParser.parseCollateClause();
        x.setCollationExpr(collateOptionExpr);



        return x;
    }

    public SQLAlterTypeStatement parseAlter() {
        SQLAlterTypeStatement x = new SQLAlterTypeStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TYPE, true);

        x.setName(exprParser.parseName());
        for (;;) {
            ISQLAlterTypeAction action = parseAlterAction();
            x.addAction(action);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        return x;
    }

    public ISQLAlterTypeAction parseAlterAction() {
        return null;
    }


    public SQLDropTypeStatement parseDrop() {
        SQLDropTypeStatement x = new SQLDropTypeStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        acceptAndNextToken(SQLToken.TokenKind.TYPE, true);

        ISQLName name = exprParser.parseName();
        x.addName(name);

        return x;
    }

}
