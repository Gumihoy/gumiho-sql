package com.gumihoy.sql.dialect.oracle.ast;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * @author kent on 2019-06-14.
 */
public interface IOracleObject extends ISQLObject {

    void accept0(IOracleASTVisitor visitor);

}
