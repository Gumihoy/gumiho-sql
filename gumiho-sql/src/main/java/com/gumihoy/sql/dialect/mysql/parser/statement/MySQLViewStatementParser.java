package com.gumihoy.sql.dialect.mysql.parser.statement;

import com.gumihoy.sql.basic.ast.expr.view.alter.ISQLAlterViewAction;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLAlterViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLDropViewStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLViewStatementParser;

/**
 * @author kent on 2019-06-25.
 */
public class MySQLViewStatementParser extends SQLViewStatementParser {


    public MySQLViewStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateViewStatement parseCreate() {
        SQLCreateViewStatement x = new SQLCreateViewStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        x.setOrReplace(exprParser.parseOrReplace());
        x.setAlgorithmSetOptionExpr(exprParser.parseAlgorithmOptionExpr());
        x.setDefinerSetOptionExpr(exprParser.parseDefinerOptionExpr());
        x.setSecurityType(exprParser.parseSQLSecurity());


        acceptAndNextToken(SQLToken.TokenKind.VIEW, true);
        x.setName(exprParser.parseName());

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (;;) {
                x.addColumn(exprParser.parseTableElement());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }


        this.acceptAndNextToken(SQLToken.TokenKind.AS, true);
        x.setSubQuery(exprParser.parseSelectQuery());
        x.setSubQueryRestriction(exprParser.parseSubQueryRestrictionClause());


        return x;
    }




    public SQLAlterViewStatement parseAlter() {
        SQLAlterViewStatement x = new SQLAlterViewStatement(this.dbType);
        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);

        x.setAlgorithmOptionExpr(exprParser.parseAlgorithmOptionExpr());
        x.setDefinerOptionExpr(exprParser.parseDefinerOptionExpr());
        x.setSecurityType(exprParser.parseSQLSecurity());

        this.acceptAndNextToken(SQLToken.TokenKind.VIEW, true);

        x.setName(exprParser.parseName());
        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (;;) {
                x.addColumn(exprParser.parseTableElement());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.AS, true);
        x.setSubQuery(exprParser.parseSelectQuery());
        x.setSubQueryRestriction(exprParser.parseSubQueryRestrictionClause());

        return x;
    }


    public SQLDropViewStatement parseDrop() {
        SQLDropViewStatement x = new SQLDropViewStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.VIEW, true);

        x.setIfExists(exprParser.parseIfExists());
        for (;;) {
            x.addName(exprParser.parseName());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        x.setBehavior(exprParser.parseDropBehavior());

        return x;
    }

}
