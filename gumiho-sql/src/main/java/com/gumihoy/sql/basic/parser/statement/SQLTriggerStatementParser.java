package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.trigger.alter.ISQLAlterTriggerAction;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLAlterTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLDropTriggerStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLTriggerStatementParser extends AbstractSQLStatementParser {


    public SQLTriggerStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateTriggerStatement parseCreate() {
        SQLCreateTriggerStatement x = new SQLCreateTriggerStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);

        return x;
    }

    public ISQLExpr parseCreateOption() {
        return null;
    }



    public SQLAlterTriggerStatement parseAlter() {
        SQLAlterTriggerStatement x = new SQLAlterTriggerStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);

        x.setName(exprParser.parseName());
        x.setAction(parseAlterAction());

        return x;
    }

    public ISQLAlterTriggerAction parseAlterAction() {
        return null;
    }


    public SQLDropTriggerStatement parseDrop() {
        SQLDropTriggerStatement x = new SQLDropTriggerStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);

        x.setIfExists(exprParser.parseIfExists());

        x.setName(exprParser.parseName());

        if (this.acceptAndNextToken(SQLToken.TokenKind.ON)) {
            x.setOnTableName(exprParser.parseName());
        }

        return x;
    }

}
