package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.role.AbstractSQLRoleAction;
import com.gumihoy.sql.basic.ast.expr.role.ISQLCreateRoleAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedByAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedExternallyAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedGloballyAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedUsingAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleNotIdentifiedAction;
import com.gumihoy.sql.basic.ast.expr.role.alter.ISQLAlterRoleAction;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLCreateRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLDropRoleStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLRoleStatementParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleRoleStatementParser extends SQLRoleStatementParser {


    public OracleRoleStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateRoleStatement parseCreate() {
        return super.parseCreate();
    }

    public ISQLCreateRoleAction parseCreateAction() {
        if (this.accept(SQLToken.TokenKind.NOT)) {
            return parseNotIdentified();
        }
        if (this.accept(SQLToken.TokenKind.IDENTIFIED)) {
            return parseIdentified();
        }
        if (this.accept(SQLToken.TokenKind.CONTAINER)) {
            return parseContainerClause();
        }
        return null;
    }

    public ISQLAlterRoleAction parseAlterAction() {
        if (this.accept(SQLToken.TokenKind.NOT)) {
            return parseNotIdentified();
        }
        if (this.accept(SQLToken.TokenKind.IDENTIFIED)) {
            return parseIdentified();
        }
        return null;
    }


    public SQLRoleNotIdentifiedAction parseNotIdentified() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.IDENTIFIED, true);
        return new SQLRoleNotIdentifiedAction();
    }

    public AbstractSQLRoleAction parseIdentified() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.IDENTIFIED)) {
            return null;
        }
        AbstractSQLRoleAction x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.BY)) {
            x = new SQLRoleIdentifiedByAction(exprParser.parseExpr());

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.USING)) {
            x = new SQLRoleIdentifiedUsingAction(exprParser.parseExpr());

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.EXTERNALLY)) {
            x = new SQLRoleIdentifiedExternallyAction();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.GLOBALLY)) {
            ISQLExpr asName = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.AS)) {
                asName = exprParser.parseExpr();
            }
            x = new SQLRoleIdentifiedGloballyAction(asName);

        }

        if (x != null) {
            AbstractSQLRoleAction.SQLContainerClause containerClause = parseContainerClause();
            x.setContainerClause(containerClause);
            return x;
        }

        throw new SQLParserException();
    }

    public AbstractSQLRoleAction.SQLContainerClause parseContainerClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CONTAINER)) {
            return null;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.EQ, true);

        AbstractSQLRoleAction.SQLContainerType type = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.CURRENT)) {
            type = AbstractSQLRoleAction.SQLContainerType.CURRENT;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.ALL)) {
            type = AbstractSQLRoleAction.SQLContainerType.ALL;
        }

        return new AbstractSQLRoleAction.SQLContainerClause(type);

    }


    public SQLDropRoleStatement parseDrop() {
        return super.parseDrop();
    }

}
