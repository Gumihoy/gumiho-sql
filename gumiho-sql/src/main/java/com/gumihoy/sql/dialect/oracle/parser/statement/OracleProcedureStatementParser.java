package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLParameterDeclaration;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.procedure.alter.ISQLAlterProcedureAction;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLAlterProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLCreateProcedureStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLProcedureStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleProcedureStatementParser extends SQLProcedureStatementParser {

    public OracleProcedureStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateProcedureStatement parseCreate() {
        SQLCreateProcedureStatement x = new SQLCreateProcedureStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        x.setOrReplace(exprParser.parseOrReplace());

        acceptAndNextToken(SQLToken.TokenKind.PROCEDURE, true);

        x.setName(exprParser.parseName());

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLParameterDeclaration parameter = exprParser.parseParameterDeclaration();
                x.addParameter(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

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
            if (this.accept(SQLToken.TokenKind.BEGIN)
                    || getExprParser().isParseCallSpec()) {
                break;
            }
            ISQLExpr declareSection = getExprParser().parseDeclareSection();
            if (declareSection == null) {
                break;
            }
            x.addDeclareSection(declareSection);
        }

        ISQLExpr asExpr;
        if (this.accept(SQLToken.TokenKind.BEGIN)) {
            asExpr = getExprParser().parseBody();

        } else if (getExprParser().isParseCallSpec()) {
            asExpr = getExprParser().parseCallSpec();

        } else {
            throw new SQLParserException("Syn Error.");
        }
        x.setAsExpr(asExpr);

        return x;
    }


    @Override
    public ISQLExpr parseCreateOption() {
        if (this.accept(SQLToken.TokenKind.AUTHID)) {
            return getExprParser().parseInvokerRightsClause();
        }

        if (this.accept(SQLToken.TokenKind.ACCESSIBLE)) {
            return getExprParser().parseAccessibleByClause();
        }

        if (this.accept(SQLToken.TokenKind.DEFAULT)) {
            return getExprParser().parseCollateClause();
        }

        return super.parseCreateOption();
    }

    @Override
    public SQLAlterProcedureStatement parseAlter() {
        return super.parseAlter();
    }

    @Override
    public ISQLAlterProcedureAction parseAlterAction() {
        if (this.accept(SQLToken.TokenKind.COMPILE)) {
            return getExprParser().parseCompileClause();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONABLE)) {
            return new ISQLAlterProcedureAction.SQLAlterProcedureEditionAbleAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NONEDITIONABLE)) {
            return new ISQLAlterProcedureAction.SQLAlterProcedureNonEditionAbleAction();
        }
        return null;
    }

    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }
}
