package com.gumihoy.sql.dialect.mariadb.parser;

import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLStatementParser;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-13.
 */
public class MariaDBStatementParser extends SQLStatementParser {

    public MariaDBStatementParser(String sql, SQLParseConfig config) {
        super(new MariaDBExprParser(sql, config), config);
    }

    public MariaDBStatementParser(MariaDBLexer lexer) {
        super(new MariaDBExprParser(lexer));
    }

    {
        dbType = DBType.MariaDB;
    }


}
