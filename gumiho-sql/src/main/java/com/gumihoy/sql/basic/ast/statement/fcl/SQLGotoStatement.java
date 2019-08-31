package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * GOTO label
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/GOTO-statement.html#GUID-89407985-3BA9-4508-9F1F-DE36878B4C89
 *
 * @author kent on 2018/6/13.
 */
public class SQLGotoStatement extends AbstractSQLStatement {

    protected ISQLName name;

    public SQLGotoStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLGotoStatement clone() {
        SQLGotoStatement x = new SQLGotoStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLGotoStatement x) {
        super.cloneTo(x);

        ISQLName labelClone = this.name.clone();
        x.setName(labelClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.GO_TO;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }
}
