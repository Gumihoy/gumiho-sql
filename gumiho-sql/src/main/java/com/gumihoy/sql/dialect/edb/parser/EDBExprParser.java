package com.gumihoy.sql.dialect.edb.parser;

import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-07-02.
 */
public class EDBExprParser extends SQLExprParser {

    public EDBExprParser(String sql) {
        super(sql);
    }

    public EDBExprParser(SQLLexer lexer) {
        super(lexer);
    }

    {
        dbType = DBType.EDB;
    }


}
