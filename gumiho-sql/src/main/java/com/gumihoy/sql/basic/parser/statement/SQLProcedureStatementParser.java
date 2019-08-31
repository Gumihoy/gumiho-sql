package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.procedure.alter.ISQLAlterProcedureAction;
import com.gumihoy.sql.basic.ast.expr.trigger.alter.ISQLAlterTriggerAction;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLAlterProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLCreateProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLDropProcedureStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class SQLProcedureStatementParser extends AbstractSQLStatementParser {


    public SQLProcedureStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateProcedureStatement parseCreate() {
        SQLCreateProcedureStatement x = new SQLCreateProcedureStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        acceptAndNextToken(SQLToken.TokenKind.PROCEDURE, true);

        x.setName(exprParser.parseName());


        return x;
    }

    public ISQLExpr parseCreateOption() {
        return null;
    }


    public SQLAlterProcedureStatement parseAlter() {
        SQLAlterProcedureStatement x = new SQLAlterProcedureStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PROCEDURE, true);

        x.setName(exprParser.parseName());
        x.setAction(parseAlterAction());

        return x;
    }

    public ISQLAlterProcedureAction parseAlterAction() {
        return null;
    }


    public SQLDropProcedureStatement parseDrop() {
        SQLDropProcedureStatement x = new SQLDropProcedureStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PROCEDURE, true);

        x.setIfExists(exprParser.parseIfExists());
        x.setName(exprParser.parseName());

        return x;
    }

    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }
}
