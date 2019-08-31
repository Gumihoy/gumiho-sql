package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.view.alter.ISQLAlterViewAction;
import com.gumihoy.sql.basic.ast.expr.view.alter.SQLAlterViewCompileAction;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLAlterViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLViewStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleViewStatementParser extends SQLViewStatementParser {

    public OracleViewStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateViewStatement parseCreate() {
        SQLCreateViewStatement x = new SQLCreateViewStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        x.setOrReplace(exprParser.parseOrReplace());

        if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
            this.acceptAndNextToken(SQLToken.TokenKind.FORCE, true);
            x.setForce(SQLCreateViewStatement.SQLForceType.NO_FORCE);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.FORCE)) {
            x.setForce(SQLCreateViewStatement.SQLForceType.FORCE);

        }

        x.setEditionAble(exprParser.parseEditionAbleType());

        acceptAndNextToken(SQLToken.TokenKind.VIEW, true);
        x.setName(exprParser.parseName());
        x.setSharingClause(exprParser.parseSharingClause());

        if(this.acceptAndNextToken(SQLToken.TokenKind.OF)) {
            x.setOfDataType(exprParser.parseDataType());

            x.setXmlSchemaSpec(getExprParser().parseXmlSchemaSpec());

            if (this.acceptAndNextToken(SQLToken.TokenKind.WITH)) {
                this.acceptAndNextToken(SQLToken.TokenKind.OBJECT, true);

                if (this.acceptAndNextToken(SQLToken.TokenKind.IDENTIFIER)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.ID)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.OID)) {

                } else {
                    throw new SQLParserException(errorInfo());
                }

                if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                    for (;;) {
                        exprParser.parseExpr();
                        if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                            break;
                        }
                    }
                    this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                }

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.UNDER)) {
                exprParser.parseName();
//                x.setSubView();
            }


        }



        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (;;) {
                x.addColumn(exprParser.parseTableElement());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }



        this.acceptAndNextToken(SQLToken.TokenKind.AS, true);
        x.setSubQuery(exprParser.parseSelectQuery());
        x.setSubQueryRestriction(exprParser.parseSubQueryRestrictionClause());

        SQLCreateViewStatement.SQLContainerType container = null;
        if(this.acceptAndNextToken(SQLToken.TokenKind.CONTAINER_MAP)) {
            container  = SQLCreateViewStatement.SQLContainerType.CONTAINER_MAP;

        } else if(this.acceptAndNextToken(SQLToken.TokenKind.CONTAINERS_DEFAULT)) {
            container  = SQLCreateViewStatement.SQLContainerType.CONTAINERS_DEFAULT;

        }
        x.setContainer(container);

        return x;
    }



    @Override
    public SQLAlterViewStatement parseAlter() {
        return super.parseAlter();
    }

    @Override
    public ISQLAlterViewAction parseAlterAction() {
        if (this.accept(SQLToken.TokenKind.ADD)) {

        }
        if (this.accept(SQLToken.TokenKind.MODIFY)) {

        }
        if (this.accept(SQLToken.TokenKind.DROP)) {

        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.COMPILE)) {
            return new SQLAlterViewCompileAction();
        }
        if (this.accept(SQLToken.TokenKind.READ)) {

        }
        if (this.accept(SQLToken.TokenKind.EDITIONABLE)) {

        }
        if (this.accept(SQLToken.TokenKind.NONEDITIONABLE)) {

        }
        return null;
    }

    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }
}
