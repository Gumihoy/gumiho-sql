package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * PARTITION BY { expr[, expr ]... | ( expr[, expr ]... ) }
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#window%20partition%20clause
 * <p>
 * partition_clause: PARTITION BY expr [, expr] ...
 * https://dev.mysql.com/doc/refman/8.0/en/window-functions-named-windows.html
 * https://dev.mysql.com/doc/refman/8.0/en/window-functions-usage.html
 *
 * @author kent on 2018/5/22.
 */
public class SQLPartitionClause extends AbstractSQLExpr {

    protected final List<ISQLExpr> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLPartitionClause clone() {
        SQLPartitionClause x = new SQLPartitionClause();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLPartitionClause x) {
        super.cloneTo(x);
        for (ISQLExpr item : items) {
            ISQLExpr itemClone = item.clone();
            this.addItem(itemClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(items, source, target, this);
        if (replace) {
            return true;
        }
        return false;
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
