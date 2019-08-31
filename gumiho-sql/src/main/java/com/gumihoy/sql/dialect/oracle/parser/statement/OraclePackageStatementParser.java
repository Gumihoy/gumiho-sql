package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.package_.alter.ISQLAlterPackageAction;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLAlterPackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLCreatePackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLDropPackageStatement;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLPackageStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class OraclePackageStatementParser extends SQLPackageStatementParser {

    public OraclePackageStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreatePackageStatement parseCreate() {
        SQLCreatePackageStatement x = new SQLCreatePackageStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        boolean orReplace = this.exprParser.parseOrReplace();
        x.setOrReplace(orReplace);

        acceptAndNextToken(SQLToken.TokenKind.PACKAGE, true);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        SQLSharingClause sharingClause = exprParser.parseSharingClause();
        x.setSharingClause(sharingClause);

        for (; ; ) {
            ISQLExpr option = parseCreateOption();
            if (option == null) {
                break;
            }
            x.addOption(option);
        }

        SQLASType as = exprParser.parseAsType(true);
        x.setAs(as);

        for (; ; ) {
            ISQLExpr item = parseCreateItem();
            if (item == null) {
                break;
            }
            x.addItem(item);
        }


        this.acceptAndNextToken(SQLToken.TokenKind.END, true);

        ISQLName endName = exprParser.parseName();
        x.setEndName(endName);


        return x;
    }

    public ISQLExpr parseCreateOption() {
        if (this.accept(SQLToken.TokenKind.DEFAULT)) {
            return exprParser.parseCollateClause();

        } else if (this.accept(SQLToken.TokenKind.AUTHID)) {
            return getExprParser().parseInvokerRightsClause();

        } else if (this.accept(SQLToken.TokenKind.ACCESSIBLE)) {
            return getExprParser().parseAccessibleByClause();

        }

        return null;
    }

    public ISQLExpr parseCreateItem() {
        if (this.accept(SQLToken.TokenKind.END)) {
            return null;
        }


        SQLLexer.SQLMake make = this.make();
        // Type Definition
        if (this.accept(SQLToken.TokenKind.TYPE)
                || this.accept(SQLToken.TokenKind.SUBTYPE)) {
            return getExprParser().parseTypeDefinition();
        }
        // Cursor Declaration
        if (this.accept(SQLToken.TokenKind.CURSOR)) {
            return getExprParser().parseCursorDeclaration();
        }
        // Function Declaration
        if (this.accept(SQLToken.TokenKind.FUNCTION)) {
            return getExprParser().parseFunctionDeclarationOrDefinition();
        }
        // Procedure Declaration
        if (this.accept(SQLToken.TokenKind.PROCEDURE)) {
            return getExprParser().parseProcedureDeclarationOrDefinition();
        }

        if (this.accept(SQLToken.TokenKind.PRAGMA)) {
            return getExprParser().parsePragma();
        }

        // item_declaration
        ISQLExpr expr = exprParser.parseExpr();
        if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTANT)) {
            this.reset(make);
            return getExprParser().parseConstantDeclaration();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTION)) {
            this.reset(make);
            return getExprParser().parseExceptionDeclaration();
        }

        this.reset(make);
        return getExprParser().parseVariableDeclaration();
    }


    public SQLAlterPackageStatement parseAlter() {
        SQLAlterPackageStatement x = new SQLAlterPackageStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PACKAGE, true);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        ISQLAlterPackageAction action = parseAlterAction();
        x.setAction(action);

        return x;
    }

    @Override
    public ISQLAlterPackageAction parseAlterAction() {

        if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONABLE)) {
            return new ISQLAlterPackageAction.SQLAlterPackageEditionAbleAction();

        } else  if (this.acceptAndNextToken(SQLToken.TokenKind.NONEDITIONABLE)) {
            return new ISQLAlterPackageAction.SQLAlterPackageNoneEditionAbleAction();

        } else if (this.accept(SQLToken.TokenKind.COMPILE)) {
            return getExprParser().parseCompileClause();

        }

        return null;
    }



    public SQLDropPackageStatement parseDrop() {
        SQLDropPackageStatement x = new SQLDropPackageStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PACKAGE, true);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }


    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }

}
