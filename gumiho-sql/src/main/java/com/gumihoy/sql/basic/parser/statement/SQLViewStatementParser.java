package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.view.alter.ISQLAlterViewAction;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLAlterViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLDropViewStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLViewStatementParser extends AbstractSQLStatementParser {


    public SQLViewStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateViewStatement parseCreate() {
        SQLCreateViewStatement x = new SQLCreateViewStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        acceptAndNextToken(SQLToken.TokenKind.VIEW, true);

        x.setName(exprParser.parseName());

        exprParser.parseTableElements(x.getColumns(), x);

        this.acceptAndNextToken(SQLToken.TokenKind.AS, true);
        x.setSubQuery(exprParser.parseSelectQuery());
        x.setSubQueryRestriction(exprParser.parseSubQueryRestrictionClause());

        return x;
    }




    public SQLAlterViewStatement parseAlter() {
        SQLAlterViewStatement x = new SQLAlterViewStatement(this.dbType);
        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.VIEW, true);
        x.setIfExists(exprParser.parseIfExists());
        x.setName(exprParser.parseName());



        x.setAction(parseAlterAction());
        return x;
    }

    public ISQLAlterViewAction parseAlterAction() {
        return null;
    }


    public SQLDropViewStatement parseDrop() {
        SQLDropViewStatement x = new SQLDropViewStatement(this.dbType);
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.VIEW, true);

        x.setIfExists(exprParser.parseIfExists());
        x.addName(exprParser.parseName());
        x.setBehavior(exprParser.parseDropBehavior());
        return x;
    }

}
