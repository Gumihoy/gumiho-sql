package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * STORE IN (  tablespace [, tablespace]... )
 *
 * @author kent on 2018/6/25.
 */
public class SQLStoreInClause extends AbstractSQLExpr {

    protected final List<ISQLExpr> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLStoreInClause clone() {
        SQLStoreInClause x = new SQLStoreInClause();
        this.cloneTo(x);
        return x;
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
