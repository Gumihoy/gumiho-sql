package com.gumihoy.sql.dialect.gpdb.ast;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.dialect.gpdb.visitor.IGPDBASTVisitor;
import com.gumihoy.sql.dialect.mysql.visitor.IMySQLASTVisitor;

/**
 * @author kent on 2019-06-14.
 */
public interface IGPDBObject extends ISQLObject {

    void accept0(IGPDBASTVisitor visitor);

}
