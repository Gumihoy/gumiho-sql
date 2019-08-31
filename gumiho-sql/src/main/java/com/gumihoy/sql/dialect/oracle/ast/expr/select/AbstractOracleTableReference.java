package com.gumihoy.sql.dialect.oracle.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.select.AbstractSQLTableReference;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * @author kent on 2019-07-06.
 */
public abstract class AbstractOracleTableReference extends AbstractSQLTableReference implements IOracleTableReference {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IOracleASTVisitor) {
            accept0((IOracleASTVisitor) visitor);
        } else {
            accept0(visitor);
        }
    }


}
