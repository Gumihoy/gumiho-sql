package com.gumihoy.sql.basic.ast.expr.select.order;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#order%20by%20clause
 * <p>
 * order_by_clause : https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Analytic-Functions.html#GUID-527832F7-63C0-4445-8C16-307FA5084056
 * <p>
 * https://www.postgresql.org/docs/devel/static/sql-select.html#SQL-ORDERBY
 *
 * @author kent onCondition 2018/2/8.
 */
public class SQLOrderByClause extends AbstractSQLExpr {

    protected boolean siblings;

    protected final List<SQLOrderByItem> items = new ArrayList<>();


    public SQLOrderByClause() {
    }

    public SQLOrderByClause(String sortKey) {
        if (sortKey == null) {
            throw new IllegalArgumentException("sortKey is null.");
        }
        SQLOrderByItem item = new SQLOrderByItem(sortKey);
        addItem(item);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLOrderByClause clone() {
        SQLOrderByClause x = new SQLOrderByClause();

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLOrderByClause x) {
        super.cloneTo(x);

        x.siblings = this.siblings;

        for (SQLOrderByItem item : this.items) {
            SQLOrderByItem itemClone = item.clone();
            x.addItem(itemClone);
        }

    }


    @Override
    public SQLObjectType getObjectType() {
        return null;
    }

    public boolean isSiblings() {
        return siblings;
    }

    public void setSiblings(boolean siblings) {
        this.siblings = siblings;
    }

    public List<SQLOrderByItem> getItems() {
        return items;
    }

    public void addItem(SQLOrderByItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }
}
