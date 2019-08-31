package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MODIFY CONSTRAINT constraint_name constraint_state [ CASCADE ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableModifyConstraintAction extends AbstractSQLAlterTableModifyTableConstraintAction {

    protected ISQLName name;

    public SQLAlterTableModifyConstraintAction(ISQLName name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, options);
//        }
    }

    @Override
    public SQLAlterTableModifyConstraintAction clone() {
        ISQLName nameClone = this.name.clone();
        SQLAlterTableModifyConstraintAction x = new SQLAlterTableModifyConstraintAction(nameClone);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableModifyConstraintAction x) {
        super.cloneTo(x);

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
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

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }
}
