package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent onCondition 2018/4/4.
 */
public class SQLLeaveStatement extends AbstractSQLStatement {



    public SQLLeaveStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    @Override
    public SQLLeaveStatement clone() {
        SQLLeaveStatement x = new SQLLeaveStatement(this.dbType);

        return x;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.LEAVE;
    }

}
