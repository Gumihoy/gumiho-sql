package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLParameterDeclaration;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.function.alter.ISQLAlterFunctionAction;
import com.gumihoy.sql.basic.ast.expr.procedure.alter.ISQLAlterProcedureAction;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLAlterFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLCreateFunctionStatement;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLFunctionStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleFunctionStatementParser extends SQLFunctionStatementParser {


    public OracleFunctionStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateFunctionStatement parseCreate() {
        SQLCreateFunctionStatement x = new SQLCreateFunctionStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        boolean orReplace = exprParser.parseOrReplace();
        x.setOrReplace(orReplace);

        acceptAndNextToken(SQLToken.TokenKind.FUNCTION, true);


        x.setName(exprParser.parseName());

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (;;) {
                SQLParameterDeclaration parameter = exprParser.parseParameterDeclaration();
                x.addParameter(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }


        this.acceptAndNextToken(SQLToken.TokenKind.RETURN, true);
        ISQLDataType returnDataType = exprParser.parseDataType();
        x.setReturnDataType(returnDataType);

        SQLSharingClause sharingClause = exprParser.parseSharingClause();
        x.setSharingClause(sharingClause);

        for (;;) {
            ISQLExpr option = parseCreateOption();
            if (option == null) {
                break;
            }
            x.addOption(option);
        }


        SQLASType as = exprParser.parseAsType(false);
        if (as == null) {
            return x;
        }

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
            return exprParser.parseCollateClause();
        }

        if (this.accept(SQLToken.TokenKind.DETERMINISTIC)) {
            return getExprParser().parseDeterministicClause();
        }

        if (this.accept(SQLToken.TokenKind.PARALLEL_ENABLE)) {
            return getExprParser().parseParallelEnableClasue();
        }

        if (this.accept(SQLToken.TokenKind.RESULT_CACHE)) {
            return getExprParser().parseResultCacheClause();
        }
        if (this.accept(SQLToken.TokenKind.AGGREGATE)) {
            return getExprParser().parseAggregateClause();
        }
        if (this.accept(SQLToken.TokenKind.PIPELINED)) {
            return getExprParser().parsePipelinedClause();
        }


        return super.parseCreateOption();
    }

    @Override
    public SQLAlterFunctionStatement parseAlter() {
        return super.parseAlter();
    }

    @Override
    public ISQLAlterFunctionAction parserAlterAction() {
        if (this.accept(SQLToken.TokenKind.COMPILE)) {
            return getExprParser().parseCompileClause();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONABLE)) {
            return new ISQLAlterFunctionAction.SQLAlterFunctionEditionAbleAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NONEDITIONABLE)) {
            return new ISQLAlterFunctionAction.SQLAlterFunctionNonEditionAbleAction();
        }
        return null;
    }

    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }



}
