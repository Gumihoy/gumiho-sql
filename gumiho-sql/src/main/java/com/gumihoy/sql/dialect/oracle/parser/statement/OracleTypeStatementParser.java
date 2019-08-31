package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLCollateOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.type.alter.AbstractSQLAlterTypeAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.ISQLAlterTypeAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeAddAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeDropAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeModifyAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeReplaceAction;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLAlterTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLCreateTypeStatement;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLTypeStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleTypeStatementParser extends SQLTypeStatementParser {


    public OracleTypeStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateTypeStatement parseCreate() {

        SQLCreateTypeStatement x = new SQLCreateTypeStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        boolean orReplace = exprParser.parseOrReplace();
        x.setOrReplace(orReplace);

        acceptAndNextToken(SQLToken.TokenKind.TYPE, true);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        boolean force = this.acceptAndNextToken(SQLToken.TokenKind.FORCE);
        x.setForce(force);

        if (this.acceptAndNextToken(SQLToken.TokenKind.OID)) {
            x.setOidLiteral(exprParser.parseExpr());
        }

        SQLSharingClause sharingClause = exprParser.parseSharingClause();
        x.setSharingClause(sharingClause);

        SQLCollateOptionExpr collateOptionExpr = exprParser.parseCollateClause();
        x.setCollationExpr(collateOptionExpr);

        for (;;) {
            ISQLExpr property = parseCreateProperty();
            if (property == null) {
                break;
            }
            x.addProperty(property);
        }





        return x;
    }

    public ISQLExpr parseCreateProperty() {
        if (this.accept(SQLToken.TokenKind.AUTHID)) {
            return getExprParser().parseInvokerRightsClause();
        }

        if (this.accept(SQLToken.TokenKind.ACCESSIBLE)) {
            return getExprParser().parseAccessibleByClause();
        }
        return null;
    }





    @Override
    public SQLAlterTypeStatement parseAlter() {
        return super.parseAlter();
    }

    @Override
    public ISQLAlterTypeAction parseAlterAction() {
        SQLLexer.SQLMake make = this.make();

        if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONABLE)) {
            return new ISQLAlterTypeAction.SQLAlterTypeEditionAbleAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NONEDITIONABLE)) {
            return new ISQLAlterTypeAction.SQLAlterTypeNoneEditionAbleAction();
        }

        if (this.accept(SQLToken.TokenKind.COMPILE)) {
            return getExprParser().parseCompileClause();
        }

        if (this.accept(SQLToken.TokenKind.REPLACE)) {
            return parseAlterTypeReplaceAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.RESET)) {
            return new ISQLAlterTypeAction.SQLAlterTypeResetAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.INSTANTIABLE)) {
                return new ISQLAlterTypeAction.SQLAlterTypeNotInstantiableAction();
            }
            if (this.acceptAndNextToken(SQLToken.TokenKind.FINAL)) {
                return new ISQLAlterTypeAction.SQLAlterTypeNotFinalAction();
            }
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.INSTANTIABLE)) {
            return new ISQLAlterTypeAction.SQLAlterTypeInstantiableAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.FINAL)) {
            return new ISQLAlterTypeAction.SQLAlterTypeFinalAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ADD)) {
            AbstractSQLAlterTypeAction x = null;
            if (getExprParser().isParseSubProgramSpec()) {
                x = new SQLAlterTypeAddAction(getExprParser().parseSubProgramSpec());

            } else if (getExprParser().isParseMapOrderFunctionSpec()) {
                x = new SQLAlterTypeAddAction(getExprParser().parseMapOrderFunctionSpec());

            } else if (this.accept(SQLToken.TokenKind.ATTRIBUTE)) {
                x = new SQLAlterTypeAddAction(parseAttributeClause());

            }


            if (x != null) {
                AbstractSQLAlterTypeAction.ISQLDependentHandlingClause  dependentHandlingClause = parseDependentHandlingClause();
                x.setDependentHandlingClause(dependentHandlingClause);
                return x;
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MODIFY)) {
            AbstractSQLAlterTypeAction x = null;
            if (this.accept(SQLToken.TokenKind.ATTRIBUTE)) {
                x = new SQLAlterTypeModifyAction(parseAttributeClause());

            } else if (this.accept(SQLToken.TokenKind.LIMIT)) {
                x = new SQLAlterTypeModifyAction(parseLimitClause());

            } else if (this.accept(SQLToken.TokenKind.ELEMENT)) {
                x = new SQLAlterTypeModifyAction(parseElementTypeClause());
            }

            if (x != null) {
                AbstractSQLAlterTypeAction.ISQLDependentHandlingClause dependentHandlingClause = parseDependentHandlingClause();
                x.setDependentHandlingClause(dependentHandlingClause);
                return x;
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {
            AbstractSQLAlterTypeAction x = null;

            if (getExprParser().isParseSubProgramSpec()) {
                x = new SQLAlterTypeDropAction(getExprParser().parseSubProgramSpec());
            }
            if (getExprParser().isParseMapOrderFunctionSpec()) {
                x = new SQLAlterTypeDropAction(getExprParser().parseMapOrderFunctionSpec());
            }

            if (this.accept(SQLToken.TokenKind.ATTRIBUTE)) {
                x = new SQLAlterTypeDropAction(parseAttributeClause());
            }

            if (x != null) {
                AbstractSQLAlterTypeAction.ISQLDependentHandlingClause dependentHandlingClause = parseDependentHandlingClause();
                x.setDependentHandlingClause(dependentHandlingClause);
                return x;
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        return null;
    }


    public SQLAlterTypeReplaceAction parseAlterTypeReplaceAction() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.REPLACE)) {
            return null;
        }
        SQLAlterTypeReplaceAction x = new SQLAlterTypeReplaceAction();

        for (;;) {
            if (this.accept(SQLToken.TokenKind.AS)) {
                break;
            }

            ISQLExpr property = null;
            if (this.accept(SQLToken.TokenKind.AUTHID)) {
                property = getExprParser().parseInvokerRightsClause();

            } else if (this.accept(SQLToken.TokenKind.ACCESSIBLE)) {
                property = getExprParser().parseAccessibleByClause();

            }
            x.addProperty(property);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.AS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.OBJECT, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (;;) {
            ISQLExpr parameter = null;
            if (getExprParser().isParseElementSpec()) {
                getExprParser().parseElementSpec();
            } else {
                parameter = exprParser.parseParameterDeclaration();
            }
            x.addParameter(parameter);

            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }





    public ISQLAlterTypeAction.SQLAttributeClause parseAttributeClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ATTRIBUTE)) {
            return null;
        }
        ISQLAlterTypeAction.SQLAttributeClause x = new ISQLAlterTypeAction.SQLAttributeClause();

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (;;) {
                x.addParameter(exprParser.parseParameterDeclaration());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        } else {
            x.setParen(false);
            x.addParameter(exprParser.parseParameterDeclaration());
        }

        return x;
    }

    public ISQLAlterTypeAction.SQLLimitClause parseLimitClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.LIMIT)) {
            return null;
        }

        return new ISQLAlterTypeAction.SQLLimitClause(exprParser.parseExpr());
    }
    public ISQLAlterTypeAction.SQLElementTypeClause parseElementTypeClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ELEMENT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.TYPE, true);
        return new ISQLAlterTypeAction.SQLElementTypeClause(exprParser.parseDataType());
    }




    public AbstractSQLAlterTypeAction.ISQLDependentHandlingClause parseDependentHandlingClause() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.INVALIDATE)) {
            return new AbstractSQLAlterTypeAction.SQLDependentHandlingInvalidateClause();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.CASCADE)) {
            AbstractSQLAlterTypeAction.SQLDependentHandlingCascadeClause x = new AbstractSQLAlterTypeAction.SQLDependentHandlingCascadeClause();

            AbstractSQLAlterTypeAction.SQLCascadeOptionType optionType = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.INCLUDING)) {
                this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);
                this.acceptAndNextToken(SQLToken.TokenKind.DATA, true);

                optionType = AbstractSQLAlterTypeAction.SQLCascadeOptionType.INCLUDING_TABLE_DATA;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
                this.acceptAndNextToken(SQLToken.TokenKind.INCLUDING, true);
                this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);
                this.acceptAndNextToken(SQLToken.TokenKind.DATA, true);

                optionType = AbstractSQLAlterTypeAction.SQLCascadeOptionType.NOT_INCLUDING_TABLE_DATA;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.CONVERT)) {
                this.acceptAndNextToken(SQLToken.TokenKind.TO, true);
                this.acceptAndNextToken(SQLToken.TokenKind.SUBSTITUTABLE, true);

                optionType = AbstractSQLAlterTypeAction.SQLCascadeOptionType.CONVERT_TO_SUBSTITUTABLE;

            }
            x.setOptionType(optionType);

            boolean force = this.acceptAndNextToken(SQLToken.TokenKind.FORCE);
            x.setForce(force);

            x.setExceptionsClause(getExprParser().parseExceptionsClause());


            return x;
        }


        return null;
    }


    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }
}
