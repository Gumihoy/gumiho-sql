package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLAlterSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLCreateSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLDropSchemaStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-07-10.
 */
public class SQLSchemaStatementParser extends AbstractSQLStatementParser {

    public SQLSchemaStatementParser(SQLLexer lexer) {
        super(lexer);
    }

    public SQLSchemaStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateSchemaStatement parseCreate() {
        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.SCHEMA, true);

        SQLCreateSchemaStatement x = new SQLCreateSchemaStatement(this.dbType);

        boolean ifNotExists = exprParser.parseIfNotExists();
        x.setIfNotExists(ifNotExists);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }

    public SQLAlterSchemaStatement parseAlter() {
        acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        acceptAndNextToken(SQLToken.TokenKind.SCHEMA, true);

        SQLAlterSchemaStatement x = new SQLAlterSchemaStatement(this.dbType);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }


    public SQLDropSchemaStatement parseDrop() {
        acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        acceptAndNextToken(SQLToken.TokenKind.SCHEMA, true);

        SQLDropSchemaStatement x = new SQLDropSchemaStatement(this.dbType);

        boolean ifExists = exprParser.parseIfExists();
        x.setIfExists(ifExists);

        ISQLName name = exprParser.parseName();
        x.addName(name);
        return x;
    }

}
