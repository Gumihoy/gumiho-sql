package com.gumihoy.sql.dialect.mysql.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDMLEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerOrderingClause;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLAlterTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLDropTriggerStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLTriggerStatementParser;

/**
 * @author kent on 2019-06-25.
 */
public class MySQLTriggerStatementParser extends SQLTriggerStatementParser {


    public MySQLTriggerStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateTriggerStatement parseCreate() {
        SQLCreateTriggerStatement x = new SQLCreateTriggerStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        x.setDefinerExpr(exprParser.parseDefinerOptionExpr());
        acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);
        x.setName(exprParser.parseName());

        SQLCreateTriggerStatement.SQLTriggerActionTime actionTime = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.BEFORE)) {
            actionTime = SQLCreateTriggerStatement.SQLTriggerActionTime.BEFORE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.AFTER)) {
            actionTime = SQLCreateTriggerStatement.SQLTriggerActionTime.AFTER;

        } else {
            throw new SQLParserException("");
        }
        x.setActionTime(actionTime);


        SQLTriggerEvent event = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.DELETE)) {
            event = SQLTriggerDMLEvent.of(SQLTriggerDMLEvent.SQLTriggerDMLEventType.DELETE);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INSERT)) {
            event = SQLTriggerDMLEvent.of(SQLTriggerDMLEvent.SQLTriggerDMLEventType.INSERT);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.UPDATE)) {
            event = SQLTriggerDMLEvent.of(SQLTriggerDMLEvent.SQLTriggerDMLEventType.UPDATE);

        } else {
            throw new SQLParserException(errorInfo());
        }
        x.addEvent(event);

        this.acceptAndNextToken(SQLToken.TokenKind.ON, true);
        x.setOnExpr(exprParser.parseName());

        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            this.acceptAndNextToken(SQLToken.TokenKind.EACH, true);
            this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);
            x.setForEachType(SQLCreateTriggerStatement.SQLTriggerForEachType.FOR_EACH_ROW);
        }


        x.setOrderingClause(parseTriggerOrder());

        ISQLExpr triggerBody = exprParser.parseBlock();
        x.setTriggerBody(triggerBody);

        return x;
    }


    public SQLTriggerOrderingClause parseTriggerOrder() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOLLOWS)) {

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.PRECEDES)) {

        }


        return null;
    }


    public SQLAlterTriggerStatement parseAlter() {
        throw new UnsupportedOperationException();
    }


    public SQLDropTriggerStatement parseDrop() {
        return super.parseDrop();
    }

}
