package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.index.alter.ISQLAlterIndexAction;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLIndexStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-07-10.
 */
public class OracleIndexStatementParser extends SQLIndexStatementParser {

    public OracleIndexStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }


    @Override
    public SQLCreateIndexStatement parseCreate() {
        return super.parseCreate();
    }


    @Override
    public SQLAlterIndexStatement parseAlter() {
        SQLAlterIndexStatement x = new SQLAlterIndexStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.INDEX, true);

        x.setName(exprParser.parseName());

        for (; ; ) {
            ISQLAlterIndexAction action = parseAlterAction();
            if (action == null) {
                break;
            }
            x.addAction(action);
        }

        return x;
    }

    @Override
    public ISQLAlterIndexAction parseAlterAction() {
        SQLLexer.SQLMake make = this.make();


        if (this.acceptAndNextToken(SQLToken.TokenKind.ADD)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseAddPartition();
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MODIFY)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {
                this.reset(make);
                return parseModifyDefaultAttributes();
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseModifyPartition();
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION)) {
                this.reset(make);
                return parseModifySubPartition();
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.RENAME)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseRenamePartition();
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION)) {
                this.reset(make);
                return parseRenameSubPartition();
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseDropPartition();
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.SPLIT)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseSplitPartition();
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.COALESCE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseCoalescePartition();
            }

            this.reset(make);
            throw new SQLParserException(errorInfo());
        }

        return null;
    }

    @Override
    public SQLDropIndexStatement parseDrop() {
        SQLDropIndexStatement x = new SQLDropIndexStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.INDEX, true);

        x.addName(exprParser.parseExpr());

        x.setOnline(this.acceptAndNextToken(SQLToken.TokenKind.ONLINE));
        x.setForce(this.acceptAndNextToken(SQLToken.TokenKind.FORCE));

        if (this.acceptAndNextToken(SQLToken.TokenKind.DEFERRED)) {
            this.acceptAndNextToken(SQLToken.TokenKind.INVALIDATION, true);
        } else if (this.acceptAndNextToken(SQLToken.TokenKind.IMMEDIATE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.INVALIDATION, true);
        }
        return x;
    }
}
