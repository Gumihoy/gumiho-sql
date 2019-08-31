package com.gumihoy.sql.dialect.mysql.parser;

import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLStatementParser;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-13.
 */
public class MySQLStatementParser extends SQLStatementParser {

    public MySQLStatementParser(String sql, SQLParseConfig config) {
        super(new MySQLExprParser(sql, config), config);
    }

    public MySQLStatementParser(SQLLexer lexer) {
        super(new MySQLExprParser(lexer));
    }

    {
        dbType = DBType.MySQL;
    }


}
