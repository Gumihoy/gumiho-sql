package com.gumihoy.sql.dialect.mariadb.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLAlterTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLDropTriggerStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLTriggerStatementParser;
import com.gumihoy.sql.dialect.mariadb.parser.MariaDBExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class MariaDBTriggerStatementParser extends SQLTriggerStatementParser {


    public MariaDBTriggerStatementParser(MariaDBExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateTriggerStatement parseCreate() {
        SQLCreateTriggerStatement x = new SQLCreateTriggerStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        acceptAndNextToken(SQLToken.TokenKind.TABLE, true);

        return x;
    }


    public SQLAlterTriggerStatement parseAlter() {
        SQLAlterTriggerStatement x = new SQLAlterTriggerStatement(this.dbType);
        return x;
    }




    public SQLDropTriggerStatement parseDrop() {
        SQLDropTriggerStatement x = new SQLDropTriggerStatement(this.dbType);
        return x;
    }

}
