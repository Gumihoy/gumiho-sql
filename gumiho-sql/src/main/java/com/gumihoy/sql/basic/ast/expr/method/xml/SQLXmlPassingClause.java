package com.gumihoy.sql.basic.ast.expr.method.xml;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * PASSING [ BY VALUE ] expr [ AS identifier ] [, expr [ AS identifier ]]...
 *
 * @author kent on 2018/5/28.
 */
public class SQLXmlPassingClause extends AbstractSQLExpr {

    protected boolean byValue;

    protected final List<ISQLExpr> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//        }
    }


    public boolean isByValue() {
        return byValue;
    }

    public void setByValue(boolean byValue) {
        this.byValue = byValue;
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
