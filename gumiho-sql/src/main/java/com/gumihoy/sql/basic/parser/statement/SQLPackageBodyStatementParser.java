package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLAlterPackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLCreatePackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLDropPackageBodyStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class SQLPackageBodyStatementParser extends AbstractSQLStatementParser {


    public SQLPackageBodyStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreatePackageBodyStatement parseCreate() {
        throw new UnsupportedOperationException();
    }


    public SQLAlterPackageBodyStatement parseAlter() {
        throw new UnsupportedOperationException();
    }


    public SQLDropPackageBodyStatement parseDrop() {
        throw new UnsupportedOperationException();
    }

}
