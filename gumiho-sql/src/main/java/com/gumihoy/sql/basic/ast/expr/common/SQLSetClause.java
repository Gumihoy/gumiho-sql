package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.ast.expr.update.ISQLUpdateSetClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * set xx = xx, xx=xx
 *
 * @author kent on 2019-07-07.
 */
public class SQLSetClause extends AbstractSQLExpr implements ISQLInsertValuesClause, ISQLUpdateSetClause {

    protected final List<ISQLExpr> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLSetClause clone() {
        return null;
    }

    public List<ISQLExpr> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        items.add(item);
    }


}
