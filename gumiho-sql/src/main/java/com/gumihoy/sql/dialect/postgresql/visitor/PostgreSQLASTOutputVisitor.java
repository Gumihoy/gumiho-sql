package com.gumihoy.sql.dialect.postgresql.visitor;

import com.gumihoy.sql.basic.visitor.SQLASTOutputVisitor;
import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;

/**
 * @author kent on 2019-06-14.
 */
public class PostgreSQLASTOutputVisitor extends SQLASTOutputVisitor implements  IPostgreSQLASTVisitor {

    public PostgreSQLASTOutputVisitor(StringBuilder appender) {
        super(appender);
    }

    public PostgreSQLASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
        super(appender, config);
    }
}
