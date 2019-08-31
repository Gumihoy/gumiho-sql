package com.gumihoy.sql.dialect.mariadb.ast;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.mariadb.visitor.IMariaDBASTVisitor;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractMariaDBObject extends AbstractSQLObject implements IMariaDBObject {

    public AbstractMariaDBObject() {
        super(DBType.MariaDB);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IMariaDBASTVisitor) {
            accept0((IMariaDBASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public abstract void accept0(IMariaDBASTVisitor visitor);
}
