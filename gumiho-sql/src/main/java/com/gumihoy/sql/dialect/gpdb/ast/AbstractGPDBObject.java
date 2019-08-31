package com.gumihoy.sql.dialect.gpdb.ast;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.gpdb.visitor.IGPDBASTVisitor;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractGPDBObject extends AbstractSQLObject implements IGPDBObject {

    public AbstractGPDBObject() {
        super(DBType.GPDB);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IGPDBASTVisitor) {
            accept0((IGPDBASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public abstract void accept0(IGPDBASTVisitor visitor);
}
