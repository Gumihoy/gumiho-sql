package com.gumihoy.sql.dialect.oracle.parser;

import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLStatementParser;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.enums.DBType;

/**
 *
 * @author kent on 2019-06-13.
 */
public class OracleStatementParser extends SQLStatementParser {


    public OracleStatementParser(String sql) {
        super(new OracleExprParser(sql));
    }

    public OracleStatementParser(String sql, SQLParseConfig config) {
        super(new OracleExprParser(sql, config), config);
    }


    {
        dbType = DBType.Oracle;
    }



}
