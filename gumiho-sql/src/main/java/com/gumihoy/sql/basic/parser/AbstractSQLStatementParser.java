package com.gumihoy.sql.basic.parser;

import com.gumihoy.sql.config.SQLParseConfig;

/**
 * @author kent on 2019-06-17.
 */
public abstract class AbstractSQLStatementParser extends SQLParser {

    protected SQLExprParser exprParser;

    public AbstractSQLStatementParser(SQLLexer lexer) {
        this(new SQLExprParser(lexer));
    }

    public AbstractSQLStatementParser(SQLExprParser exprParser) {
        super(exprParser.lexer);
        this.exprParser = exprParser;
    }

    public AbstractSQLStatementParser(SQLExprParser exprParser, SQLParseConfig config) {
        super(exprParser.lexer);
        this.exprParser = exprParser;
    }

    public SQLExprParser getExprParser() {
        return exprParser;
    }

}
