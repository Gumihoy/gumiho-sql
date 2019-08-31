package com.gumihoy.sql.dialect.gpdb.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLAlterViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLDropViewStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLViewStatementParser;

/**
 * @author kent on 2019-06-25.
 */
public class GPDBViewStatementParser extends SQLViewStatementParser {


    public GPDBViewStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateViewStatement parseCreate() {
        SQLCreateViewStatement x = new SQLCreateViewStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        acceptAndNextToken(SQLToken.TokenKind.TABLE, true);

        return x;
    }




    public SQLAlterViewStatement parseAlter() {
        SQLAlterViewStatement x = new SQLAlterViewStatement(this.dbType);
        return x;
    }




    public SQLDropViewStatement parseDrop() {
        SQLDropViewStatement x = new SQLDropViewStatement(this.dbType);
        return x;
    }

}
