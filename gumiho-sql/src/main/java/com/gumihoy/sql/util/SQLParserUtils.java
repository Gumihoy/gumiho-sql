package com.gumihoy.sql.util;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.parser.SQLStatementParser;
import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.dialect.mysql.parser.MySQLStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleStatementParser;
import com.gumihoy.sql.dialect.postgresql.parser.PostgreSQLStatementParser;
import com.gumihoy.sql.enums.DBType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author kent on 2019-06-14.
 */
public class SQLParserUtils {

    protected static final Logger log = LoggerFactory.getLogger(SQLParserUtils.class);

    public static List<ISQLObject> parseStatements(String sql, DBType dbType) {
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType, null);
        return parser.parser();
    }

    public static List<ISQLObject> parseStatements(String sql, DBType dbType, SQLParseConfig config) {
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType, config);
        return parser.parser();
    }

    public static SQLStatementParser createSQLStatementParser(String sql, DBType dbType, SQLParseConfig config) {
        switch (dbType) {
            case Oracle:
                return new OracleStatementParser(sql, config);
            case MySQL:
                return new MySQLStatementParser(sql, config);
            case EDB:
                return null;
            case PostgreSQL:
                return new PostgreSQLStatementParser(sql, config);
            default:
                return new SQLStatementParser(sql, dbType, config);
        }
    }


}
