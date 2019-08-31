package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLConnectToCurrentUserClause;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLConnectToIdentifiedByClause;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLDBLinkAuthenticationClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLAlterDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLCreateDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLDropDatabaseLinkStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-07-10.
 */
public class SQLDatabaseLinkStatementParser extends AbstractSQLStatementParser {

    public SQLDatabaseLinkStatementParser(SQLLexer lexer) {
        super(lexer);
    }

    public SQLDatabaseLinkStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateDatabaseLinkStatement parseCreate() {
        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        boolean shared = acceptAndNextToken(SQLToken.TokenKind.SHARED);
        boolean public_ = acceptAndNextToken(SQLToken.TokenKind.PUBLIC);

        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);
        acceptAndNextToken(SQLToken.TokenKind.LINK, true);

        SQLCreateDatabaseLinkStatement x = new SQLCreateDatabaseLinkStatement(this.dbType);

        x.setShared(shared);
        x.setPublic_(public_);

        ISQLName name = exprParser.parseName();
        x.setName(name);


        ISQLExpr action = null;
        if (this.accept(SQLToken.TokenKind.CONNECT)) {
            action = parseConnectTo();
        } else if (this.accept(SQLToken.TokenKind.AUTHENTICATED)) {
            action = parseAuthentication();
        }
        x.addAction(action);

        if (this.acceptAndNextToken(SQLToken.TokenKind.USING)) {
            ISQLExpr using = exprParser.parseExpr();
            x.setUsing(using);
        }

        return x;
    }


    public SQLAlterDatabaseLinkStatement parseAlter() {
        acceptAndNextToken(SQLToken.TokenKind.ALTER, true);

        boolean shared = acceptAndNextToken(SQLToken.TokenKind.SHARED);
        boolean public_ = acceptAndNextToken(SQLToken.TokenKind.PUBLIC);

        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);
        acceptAndNextToken(SQLToken.TokenKind.LINK, true);

        SQLAlterDatabaseLinkStatement x = new SQLAlterDatabaseLinkStatement(this.dbType);

        x.setShared(shared);
        x.setPublic_(public_);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        ISQLExpr action = null;
        if (this.accept(SQLToken.TokenKind.CONNECT)) {
            action = parseConnectTo();
        } else if (this.accept(SQLToken.TokenKind.AUTHENTICATED)) {
            action = parseAuthentication();
        } else {
            throw new SQLParserException("Sy missing");
        }
        x.setAction(action);

        return x;
    }


    public ISQLExpr parseConnectTo() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CONNECT)) {
            return null;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.TO, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.CURRENT_USER)) {
            return new SQLConnectToCurrentUserClause();
        }


        ISQLExpr user = exprParser.parseExpr();

        this.acceptAndNextToken(SQLToken.TokenKind.IDENTIFIED, true);
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

        ISQLExpr password = exprParser.parseExpr();

        SQLDBLinkAuthenticationClause dbLinkAuthenticationClause = parseAuthentication();

        return new SQLConnectToIdentifiedByClause(user, password, dbLinkAuthenticationClause);
    }

    public SQLDBLinkAuthenticationClause parseAuthentication() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.AUTHENTICATED)) {
            return null;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

        ISQLExpr user = exprParser.parseExpr();

        this.acceptAndNextToken(SQLToken.TokenKind.IDENTIFIED, true);
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

        ISQLExpr password = exprParser.parseExpr();

        return new SQLDBLinkAuthenticationClause(user, password);
    }


    public SQLDropDatabaseLinkStatement parseDrop() {
        acceptAndNextToken(SQLToken.TokenKind.DROP, true);

        boolean public_ = acceptAndNextToken(SQLToken.TokenKind.PUBLIC);

        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);
        acceptAndNextToken(SQLToken.TokenKind.LINK, true);

        SQLDropDatabaseLinkStatement x = new SQLDropDatabaseLinkStatement(this.dbType);

        x.setPublic_(public_);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }

}
