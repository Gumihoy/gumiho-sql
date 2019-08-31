package com.gumihoy.sql.dialect.postgresql.ast;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.postgresql.visitor.IPostgreSQLASTVisitor;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractPostgreSQLObject extends AbstractSQLObject implements IPostgreSQLObject {

    public AbstractPostgreSQLObject() {
        super(DBType.PostgreSQL);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IPostgreSQLASTVisitor) {
            accept0((IPostgreSQLASTVisitor)visitor);
        } else {
            throw new UnsupportedOperationException();
        }
    }


    public abstract void accept0(IPostgreSQLASTVisitor visitor);
}
