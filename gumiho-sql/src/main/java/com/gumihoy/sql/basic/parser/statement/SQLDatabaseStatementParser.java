package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLAlterDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLCreateDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLDropDatabaseStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-07-10.
 */
public class SQLDatabaseStatementParser extends AbstractSQLStatementParser {

    public SQLDatabaseStatementParser(SQLLexer lexer) {
        super(lexer);
    }

    public SQLDatabaseStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateDatabaseStatement parseCreate() {
        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);

        SQLCreateDatabaseStatement x = new SQLCreateDatabaseStatement(this.dbType);

        boolean ifNotExists = exprParser.parseIfNotExists();
        x.setIfNotExists(ifNotExists);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }

    public ISQLExpr parseCreateAction() {
        return null;
    }


    public SQLAlterDatabaseStatement parseAlter() {
        acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);

        SQLAlterDatabaseStatement x = new SQLAlterDatabaseStatement(this.dbType);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }


    public SQLDropDatabaseStatement parseDrop() {
        acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);

        SQLDropDatabaseStatement x = new SQLDropDatabaseStatement(this.dbType);

        boolean ifExists = exprParser.parseIfExists();
        x.setIfExists(ifExists);

        ISQLName name = exprParser.parseName();
        x.setName(name);
        return x;
    }




    // ------- PLUGGABLE
    public SQLDropDatabaseStatement parseCreatePluggable() {
        return null;
    }

}
