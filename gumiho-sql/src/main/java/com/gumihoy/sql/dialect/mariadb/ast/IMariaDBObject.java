package com.gumihoy.sql.dialect.mariadb.ast;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.dialect.mariadb.visitor.IMariaDBASTVisitor;

/**
 * @author kent on 2019-06-14.
 */
public interface IMariaDBObject extends ISQLObject {

    void accept0(IMariaDBASTVisitor visitor);

}
