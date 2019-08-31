package com.gumihoy.sql.dialect.postgresql.parser;

import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLStatementParser;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-16.
 */
public class PostgreSQLStatementParser extends SQLStatementParser  {

    public PostgreSQLStatementParser(String sql) {
        this(new PostgreSQLLexer(sql));
    }

    public PostgreSQLStatementParser(String sql, SQLParseConfig config) {
        this(new PostgreSQLLexer(sql, config), config);
    }

    public PostgreSQLStatementParser(SQLLexer lexer) {
        this(new PostgreSQLExprParser(lexer));
    }

    public PostgreSQLStatementParser(SQLLexer lexer, SQLParseConfig config) {
        this(new PostgreSQLExprParser(lexer));
    }

    public PostgreSQLStatementParser(SQLExprParser exprParser) {
        this(exprParser, null);
    }

    public PostgreSQLStatementParser(SQLExprParser exprParser, SQLParseConfig config) {
        super(exprParser, config);
    }

    {
        this.dbType = DBType.PostgreSQL;
    }
}
