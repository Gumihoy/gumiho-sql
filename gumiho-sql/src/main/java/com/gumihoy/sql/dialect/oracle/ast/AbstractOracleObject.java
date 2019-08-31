package com.gumihoy.sql.dialect.oracle.ast;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractOracleObject extends AbstractSQLObject implements IOracleObject {

    public AbstractOracleObject() {
        super(DBType.Oracle);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IOracleASTVisitor) {
            accept0((IOracleASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public abstract void accept0(IOracleASTVisitor visitor);
}
