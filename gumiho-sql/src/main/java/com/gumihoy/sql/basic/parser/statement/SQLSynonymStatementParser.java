package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.synonym.alter.ISQLAlterSynonymAction;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLAlterSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLCreateSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLDropSynonymStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLSynonymStatementParser extends AbstractSQLStatementParser {


    public SQLSynonymStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateSynonymStatement parseCreate() {
        SQLCreateSynonymStatement x = new SQLCreateSynonymStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        x.setOrReplace(exprParser.parseOrReplace());
        x.setPublic_(this.acceptAndNextToken(SQLToken.TokenKind.PUBLIC));

        acceptAndNextToken(SQLToken.TokenKind.SYNONYM, true);

        x.setName(exprParser.parseName());
        x.setSharingClause(exprParser.parseSharingClause());

        this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);

        x.setForName(exprParser.parseName());

        return x;
    }

    public SQLAlterSynonymStatement parseAlter() {
        SQLAlterSynonymStatement x = new SQLAlterSynonymStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);

        x.setPublic_(this.acceptAndNextToken(SQLToken.TokenKind.PUBLIC));

        this.acceptAndNextToken(SQLToken.TokenKind.SYNONYM, true);


        x.setName(exprParser.parseName());
        x.setAction(parseAlterAction());

        return x;
    }

    public ISQLAlterSynonymAction parseAlterAction() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONABLE)) {
            return new ISQLAlterSynonymAction.SQLAlterSynonymEditionAbleAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NONEDITIONABLE)) {
            return new ISQLAlterSynonymAction.SQLAlterSynonymNonEditionAbleAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.COMPILE)) {
            return new ISQLAlterSynonymAction.SQLAlterSynonymCompileAction();
        }
        return null;
    }


    public SQLDropSynonymStatement parseDrop() {
        SQLDropSynonymStatement x = new SQLDropSynonymStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);

        x.setPublic_(this.acceptAndNextToken(SQLToken.TokenKind.PUBLIC));

        this.acceptAndNextToken(SQLToken.TokenKind.SYNONYM, true);

        x.setIfExists(exprParser.parseIfExists());
        x.setName(exprParser.parseName());
        x.setForce(this.acceptAndNextToken(SQLToken.TokenKind.FORCE));

        return x;
    }

}
