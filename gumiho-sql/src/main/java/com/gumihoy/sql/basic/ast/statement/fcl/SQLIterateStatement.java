package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent onCondition 2018/4/4.
 */
public class SQLIterateStatement extends AbstractSQLStatement {


    public SQLIterateStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {

        }
    }

    @Override
    public SQLIterateStatement clone() {
        SQLIterateStatement x = new SQLIterateStatement(this.dbType);

        return x;
    }


    @Override
    public SQLObjectType getObjectType() {
        return null;// SQLObjectType.ITERATE;
    }


}
