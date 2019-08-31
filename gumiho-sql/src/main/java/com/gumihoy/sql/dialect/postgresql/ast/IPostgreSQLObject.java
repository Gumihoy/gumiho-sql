package com.gumihoy.sql.dialect.postgresql.ast;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.dialect.postgresql.visitor.IPostgreSQLASTVisitor;

/**
 * @author kent on 2019-06-14.
 */
public interface IPostgreSQLObject extends ISQLObject {

    void accept0(IPostgreSQLASTVisitor visitor);
}
