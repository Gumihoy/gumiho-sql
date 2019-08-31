package com.gumihoy.sql.dialect.postgresql.parser;

import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.enums.DBType;

import java.util.Set;

/**
 * @author kent on 2019-06-16.
 */
public class PostgreSQLExprParser extends SQLExprParser  {

    public PostgreSQLExprParser(String sql) {
        this(new PostgreSQLLexer(sql));
    }

    public PostgreSQLExprParser(SQLLexer lexer) {
        super(lexer);
    }

    {
        this.dbType = DBType.PostgreSQL;
    }

}
