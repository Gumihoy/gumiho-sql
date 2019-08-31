package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.package_.alter.ISQLAlterPackageAction;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLAlterPackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLCreatePackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLDropPackageStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLParserException;

/**
 * @author kent on 2019-06-25.
 */
public class SQLPackageStatementParser extends AbstractSQLStatementParser {

    public SQLPackageStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreatePackageStatement parseCreate() {
        throw new SQLParserException("CREATE PACKAGE Unsupported.");
    }


    public SQLAlterPackageStatement parseAlter() {
        throw new SQLParserException("ALTER PACKAGE Unsupported.");
    }

    public ISQLAlterPackageAction parseAlterAction() {
        return null;
    }

    public SQLDropPackageStatement parseDrop() {
        throw new SQLParserException("DROP PACKAGE Unsupported.");
    }

}
