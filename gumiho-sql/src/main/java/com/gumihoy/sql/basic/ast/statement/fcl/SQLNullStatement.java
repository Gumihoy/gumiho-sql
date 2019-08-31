package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * NULL
 *
 * @author kent on 2018/6/9.
 */
public class SQLNullStatement extends AbstractSQLStatement {

    public SQLNullStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLNullStatement clone() {
        SQLNullStatement x = new SQLNullStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.NULL;
    }
}
