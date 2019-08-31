package com.gumihoy.sql.dialect.postgresql.visitor;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent on 2019-06-14.
 */
public interface IPostgreSQLASTVisitor extends ISQLASTVisitor {


    void preVisit(ISQLObject x);

    void postVisit(ISQLObject x);

}
