package com.gumihoy.sql.basic.parser;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.enums.DBType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kent on 2019-06-15.
 */
public class SQLStatementParser extends AbstractSQLStatementParser {

    public SQLStatementParser(String sql) {
        this(sql, null);
    }

    public SQLStatementParser(String sql, DBType dbType) {
        this(new SQLExprParser(sql, dbType));
    }

    public SQLStatementParser(String sql, DBType dbType, SQLParseConfig config) {
        this(new SQLExprParser(sql, dbType), config);
    }

    public SQLStatementParser(SQLLexer lexer) {
        this(new SQLExprParser(lexer));
    }

    public SQLStatementParser(SQLExprParser exprParser) {
        this(exprParser, null);
    }

    public SQLStatementParser(SQLExprParser exprParser, SQLParseConfig config) {
        super(exprParser);
    }

    public List<ISQLObject> parser() {
        List<ISQLObject> statementList = new ArrayList<>();
        parser(statementList, null);
        return statementList;
    }

    public List<ISQLObject> parser(ISQLObject parent) {
        List<ISQLObject> statementList = new ArrayList<>();
        parser(statementList, parent);
        return statementList;
    }

    public void parser(List<ISQLObject> statementList, ISQLObject parent) {
        this.lexer.nextToken();

        if (!preParser(parent)) {
            return;
        }

        for (; ; ) {

            if (lexer.token.kind == SQLToken.TokenKind.EOF) {
                break;
            }

            if (lexer.token.kind == SQLToken.TokenKind.SEMI) {
                if (statementList.size() > 0) {
                    ISQLObject lastStmt = statementList.get(statementList.size() - 1);
                    lastStmt.setAfterSemi(true);
                }
                nextToken();
                continue;
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.SLASH)) {
                continue;
            }

            ISQLStatement stmt = exprParser.parseStatement();
            if (stmt == null) {
                break;
            }
            addStatement(statementList, stmt, parent);
        }


        if (!this.acceptAndNextToken(SQLToken.TokenKind.EOF)) {
            throw new SQLParserException(errorInfo());
        }
    }


    public boolean preParser(ISQLObject parent) {
        return true;
    }


    private void addStatement(List<ISQLObject> statementList, ISQLObject stmt, ISQLObject parent) {
        if (stmt == null) {
            return;
        }
        stmt.setParent(parent);
        statementList.add(stmt);
    }

}
