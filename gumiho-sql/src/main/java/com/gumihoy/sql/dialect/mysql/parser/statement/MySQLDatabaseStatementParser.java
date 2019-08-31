package com.gumihoy.sql.dialect.mysql.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.database.SQLUpgradeDataDirectoryNameExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLAlterDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLCreateDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLDropDatabaseStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLDatabaseStatementParser;
import com.gumihoy.sql.dialect.mysql.parser.MySQLExprParser;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-25.
 */
public class MySQLDatabaseStatementParser extends SQLDatabaseStatementParser {

    public MySQLDatabaseStatementParser(SQLLexer lexer) {
        this(new MySQLExprParser(lexer));
    }

    public MySQLDatabaseStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }

    {
        this.dbType = DBType.MySQL;
    }


    @Override
    public SQLCreateDatabaseStatement parseCreate() {
        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);

        SQLCreateDatabaseStatement x = new SQLCreateDatabaseStatement(this.dbType);

        boolean ifNotExists = exprParser.parseIfNotExists();
        x.setIfNotExists(ifNotExists);

        ISQLName name = exprParser.parseName();
        x.setName(name);


        for (; ; ) {
            ISQLExpr action = parseCreateAction();
            if (action == null) {
                break;
            }
            x.addAction(action);
        }
        return x;
    }

    @Override
    public ISQLExpr parseCreateAction() {
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
    public SQLAlterDatabaseStatement parseAlter() {
        acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);

        SQLAlterDatabaseStatement x = new SQLAlterDatabaseStatement(this.dbType);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        for (; ; ) {
            ISQLExpr item = parseAlterOption();
            if (item == null) {
                break;
            }
            x.addItem(item);
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
    public SQLDropDatabaseStatement parseDrop() {
        return super.parseDrop();
    }


}
