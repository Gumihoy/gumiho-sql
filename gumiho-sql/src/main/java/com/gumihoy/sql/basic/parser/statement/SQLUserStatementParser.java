package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLAlterUserStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLCreateUserStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLDropUserStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLUserStatementParser extends AbstractSQLStatementParser {


    public SQLUserStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateUserStatement parseCreate() {
        SQLCreateUserStatement x = new SQLCreateUserStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        acceptAndNextToken(SQLToken.TokenKind.TABLE, true);

        return x;
    }

    public SQLAlterUserStatement parseAlter() {
        SQLAlterUserStatement x = new SQLAlterUserStatement(this.dbType);
        return x;
    }




    public SQLDropUserStatement parseDrop() {
        SQLDropUserStatement x = new SQLDropUserStatement(this.dbType);
        return x;
    }

}
