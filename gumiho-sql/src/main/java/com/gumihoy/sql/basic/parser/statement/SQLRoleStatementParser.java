package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.role.ISQLCreateRoleAction;
import com.gumihoy.sql.basic.ast.expr.role.alter.ISQLAlterRoleAction;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLAlterRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLCreateRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLDropRoleStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLRoleStatementParser extends AbstractSQLStatementParser {


    public SQLRoleStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateRoleStatement parseCreate() {
        SQLCreateRoleStatement x = new SQLCreateRoleStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.ROLE, true);

        x.setIfNotExists(exprParser.parseIfNotExists());

        for (;;) {
            x.addName(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        for (;;) {
            ISQLCreateRoleAction action = parseCreateAction();
            if (action == null) {
                break;
            }
            x.addAction(action);
        }

        return x;
    }

    public ISQLCreateRoleAction parseCreateAction() {
        return null;
    }


    public SQLAlterRoleStatement parseAlter() {
        SQLAlterRoleStatement x = new SQLAlterRoleStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.ROLE, true);

        x.setName(exprParser.parseName());

        for (;;) {
            ISQLAlterRoleAction action = parseAlterAction();
            if (action == null) {
                break;
            }
            x.addAction(action);
        }

        return x;
    }
    public ISQLAlterRoleAction parseAlterAction() {
        return null;
    }



    public SQLDropRoleStatement parseDrop() {
        SQLDropRoleStatement x = new SQLDropRoleStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.ROLE, true);

        x.setIfExists(exprParser.parseIfExists());

        for (;;) {
            x.addName(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        return x;
    }

}
