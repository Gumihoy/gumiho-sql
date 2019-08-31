package com.gumihoy.sql.basic.ast.expr.common;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * WINDOW   <new window name> AS
 * <left paren>   [ <existing window name> ] [ <window partition clause> ] [ <window order clause> ] [ <window frame clause> ] <right paren>
 * <p>
 * [ { <comma> <window definition> }... ]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#window%20clause
 * <p>
 * <p>
 * WINDOW window_name AS (  [window_name] [partition_clause] [order_clause] [frame_clause] ) [, window_name AS (window_spec)] ..
 * https://dev.mysql.com/doc/refman/8.0/en/window-functions-named-windows.html
 *
 * @author kent on 2018/5/2.
 */
public class SQLWindowClause extends AbstractSQLExpr {

    public final List<SQLWindowClauseItem> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }


    @Override
    public SQLWindowClause clone() {
        return null;
    }

    public List<SQLWindowClauseItem> getItems() {
        return items;
    }

    public void addItem(SQLWindowClauseItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        items.add(item);
    }

    /**
     * name as ([window_name] [partition_clause] [order_clause] [frame_clause])
     */
    public static class SQLWindowClauseItem extends AbstractSQLExpr {

        protected ISQLName name;
        protected SQLWindowSpecification specification;

        public SQLWindowClauseItem() {
        }

        public SQLWindowClauseItem(ISQLName name, SQLWindowSpecification specification) {
            setName(name);
            setSpecification(specification);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);

            }
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            this.name = name;
        }

        public SQLWindowSpecification getSpecification() {
            return specification;
        }

        public void setSpecification(SQLWindowSpecification specification) {
            setChildParent(specification);
            this.specification = specification;
        }
    }


}
