package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ( [expr [, expr ]] ...)
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Expression-Lists.html#GUID-5CC8FC75-813B-44AA-8737-D940FA887D1E
 *
 * @author kent on 2018/5/11.
 */
public class SQLListExpr extends AbstractSQLExpr {

    protected final List<ISQLExpr> items = new ArrayList<>();

    public SQLListExpr() {
    }

    public SQLListExpr(ISQLExpr item) {
        this.items.add(item);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLListExpr clone() {
        SQLListExpr x = new SQLListExpr();
        this.cloneTo(x);
        return x;
    }

    @Override
    public void cloneTo(ISQLObject x) {
        super.cloneTo(x);

        for (ISQLExpr item : this.items) {
            ISQLExpr itemClone = item.clone();
            addItem(itemClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return replaceInList(items, source, target, this);
    }

    public List<ISQLExpr> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }
}
