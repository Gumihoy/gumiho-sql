package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * CONTINUE [ label ] [ WHEN boolean-expression ];
 * https://www.postgresql.org/docs/9.3/static/plpgsql-control-structures.html
 *
 * CONTINUE [ label ] [ WHEN expr ] ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CONTINUE-statement.html#GUID-3ED7E5D5-E2D0-42D1-8A7F-97FFC7372775
 *
 * @author kent on 2018/6/13.
 */
public class SQLContinueStatement extends AbstractSQLStatement {

    protected ISQLName name;
    protected ISQLExpr condition;

    public SQLContinueStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
    if (visitor.visit(this)) {
        this.acceptChild(visitor, name);
        this.acceptChild(visitor, condition);
    }
    }

    @Override
    public SQLContinueStatement clone() {
        SQLContinueStatement x = new SQLContinueStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLContinueStatement x) {
        super.cloneTo(x);

        if (this.name != null) {
            ISQLName nameClone = this.name.clone();
            x.setName(nameClone);
        }

        if (this.condition != null) {
            ISQLExpr conditionClone = this.condition.clone();
            x.setCondition(conditionClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

        if (source == condition) {
            setCondition(target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.CONTINUE;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLExpr getCondition() {
        return condition;
    }

    public void setCondition(ISQLExpr condition) {
        setChildParent(condition);
        this.condition = condition;
    }
}
