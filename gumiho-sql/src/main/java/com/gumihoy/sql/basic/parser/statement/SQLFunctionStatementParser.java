package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.function.alter.ISQLAlterFunctionAction;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLAlterFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLCreateFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLDropFunctionStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLFunctionStatementParser extends AbstractSQLStatementParser {


    public SQLFunctionStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateFunctionStatement parseCreate() {

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.FUNCTION, true);

        SQLCreateFunctionStatement x = new SQLCreateFunctionStatement(this.dbType);

        x.setName(exprParser.parseName());


        for (;;) {
            ISQLExpr option = exprParser.parseParameterDeclaration();
            if (option == null) {
                break;
            }
            x.addOption(option);
        }


        for (;;) {
            ISQLExpr option = parseCreateOption();
            if (option == null) {
                break;
            }
            x.addOption(option);
        }




        return x;
    }


    public ISQLExpr parseCreateOption() {
        return null;
    }



    public SQLAlterFunctionStatement parseAlter() {
        SQLAlterFunctionStatement x = new SQLAlterFunctionStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION, true);

        x.setName(exprParser.parseName());
        x.setAction(parserAlterAction());


        return x;
    }

    public ISQLAlterFunctionAction parserAlterAction() {
        return null;
    }



    public SQLDropFunctionStatement parseDrop() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION, true);

        SQLDropFunctionStatement x = new SQLDropFunctionStatement(this.dbType);

        boolean ifExists = exprParser.parseIfExists();
        x.setIfExists(ifExists);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }

}
