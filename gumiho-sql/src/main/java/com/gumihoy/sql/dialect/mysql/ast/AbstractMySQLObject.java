package com.gumihoy.sql.dialect.mysql.ast;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.mysql.visitor.IMySQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import lombok.Data;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractMySQLObject extends AbstractSQLObject implements IMySQLObject {

    public AbstractMySQLObject() {
        super(DBType.MySQL);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IMySQLASTVisitor) {
            accept0((IMySQLASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public abstract void accept0(IMySQLASTVisitor visitor);
}
