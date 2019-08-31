package com.gumihoy.sql.dialect.mysql.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.database.SQLUpgradeDataDirectoryNameExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLAlterSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLCreateSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLDropSchemaStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLSchemaStatementParser;
import com.gumihoy.sql.dialect.mysql.parser.MySQLExprParser;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-25.
 */
public class MySQLSchemaStatementParser extends SQLSchemaStatementParser {

    public MySQLSchemaStatementParser(SQLLexer lexer) {
        this(new MySQLExprParser(lexer));
    }

    public MySQLSchemaStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }

    {
        this.dbType = DBType.MySQL;
    }


    @Override
    public SQLCreateSchemaStatement parseCreate() {
        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.SCHEMA, true);

        SQLCreateSchemaStatement x = new SQLCreateSchemaStatement(this.dbType);

        boolean ifNotExists = exprParser.parseIfNotExists();
        x.setIfNotExists(ifNotExists);

        ISQLName name = exprParser.parseName();
        x.setName(name);


        for (; ; ) {
            ISQLExpr item = parseCreateOption();
            if (item == null) {
                break;
            }
            x.addAction(item);
        }
        return x;
    }

    public ISQLExpr parseCreateOption() {
        SQLLexer.SQLMake make = this.make();

        acceptAndNextToken(SQLToken.TokenKind.DEFAULT);

        if (acceptAndNextToken(SQLToken.TokenKind.CHARACTER)) {
            this.reset(make);

            return exprParser.parseCharacterSetClause();

        } else if (acceptAndNextToken(SQLToken.TokenKind.COLLATE)) {
            this.reset(make);

            return exprParser.parseCollateClause();
        }

        this.reset(make);
        return null;
    }


    @Override
    public SQLAlterSchemaStatement parseAlter() {
        acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        acceptAndNextToken(SQLToken.TokenKind.SCHEMA, true);

        SQLAlterSchemaStatement x = new SQLAlterSchemaStatement(this.dbType);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        for (; ; ) {
            ISQLExpr item = parseAlterOption();
            if (item == null) {
                break;
            }
            x.addAction(item);
        }
        return x;
    }

    public ISQLExpr parseAlterOption() {
        SQLLexer.SQLMake make = this.make();

        acceptAndNextToken(SQLToken.TokenKind.DEFAULT);

        if (acceptAndNextToken(SQLToken.TokenKind.CHARACTER)) {
            this.reset(make);

            return exprParser.parseCharacterSetClause();

        } else if (acceptAndNextToken(SQLToken.TokenKind.COLLATE)) {
            this.reset(make);

            return exprParser.parseCollateClause();

        } else if (acceptAndNextToken(SQLToken.TokenKind.UPGRADE)) {
            acceptAndNextToken(SQLToken.TokenKind.DATA, true);
            acceptAndNextToken(SQLToken.TokenKind.DIRECTORY, true);
            acceptAndNextToken(SQLToken.TokenKind.NAME, true);

            return new SQLUpgradeDataDirectoryNameExpr();
        }

        this.reset(make);
        return null;
    }


    @Override
    public SQLDropSchemaStatement parseDrop() {
        return super.parseDrop();
    }


}
