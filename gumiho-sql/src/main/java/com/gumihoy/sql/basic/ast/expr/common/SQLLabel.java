package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * <<label>>
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLLabel extends AbstractSQLExpr {

    protected ISQLName label;

    public SQLLabel(ISQLName label) {
        setLabel(label);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, label);
        }
    }

    @Override
    public SQLLabel clone() {
        ISQLName labelClone = label.clone();
        return new SQLLabel(labelClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == label
                && target instanceof ISQLName) {
            setLabel((ISQLName) target);
            return true;
        }
        return false;
    }

    public ISQLName getLabel() {
        return label;
    }

    public void setLabel(ISQLName label) {
        setChildParent(label);
        this.label = label;
    }
}
