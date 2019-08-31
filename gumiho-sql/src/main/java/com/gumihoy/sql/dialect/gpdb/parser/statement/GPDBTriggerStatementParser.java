package com.gumihoy.sql.dialect.gpdb.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLAlterTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLDropTriggerStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLTriggerStatementParser;

/**
 * @author kent on 2019-06-25.
 */
public class GPDBTriggerStatementParser extends SQLTriggerStatementParser {


    public GPDBTriggerStatementParser(SQLExprParser exprParser) {
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
