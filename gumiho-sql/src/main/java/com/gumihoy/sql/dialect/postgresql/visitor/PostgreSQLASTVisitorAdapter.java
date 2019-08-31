package com.gumihoy.sql.dialect.postgresql.visitor;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.visitor.SQLASTVisitorAdapter;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * @author kent on 2019-06-14.
 */
public class PostgreSQLASTVisitorAdapter  extends SQLASTVisitorAdapter implements IPostgreSQLASTVisitor {


    @Override
    public void preVisit(ISQLObject x) {

    }

    @Override
    public void postVisit(ISQLObject x) {

    }
}
