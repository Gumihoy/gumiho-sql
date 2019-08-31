package com.gumihoy.sql.dialect.mysql.ast;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.dialect.mysql.visitor.IMySQLASTVisitor;

/**
 * @author kent on 2019-06-14.
 */
public interface IMySQLObject extends ISQLObject {

    void accept0(IMySQLASTVisitor visitor);

}
