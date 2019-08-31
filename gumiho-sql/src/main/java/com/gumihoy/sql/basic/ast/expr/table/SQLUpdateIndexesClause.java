package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * UPDATE INDEXES [ ( index ( update_index_partition | update_index_subpartition ) [, index ( update_index_partition | update_index_subpartition )]... )]
 * <p>
 * update_all_indexes_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public class SQLUpdateIndexesClause extends AbstractSQLExpr implements ISQLUpdateIndexClause {

    protected final List<Item> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//        }
    }

    @Override
    public SQLUpdateIndexesClause clone() {
        return null;
    }


    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }

    /**
     * index ( update_index_partition  [, update_index_subpartition]... )
     */
    public static class Item extends AbstractSQLExpr {
        protected ISQLExpr name;
        protected final List<ISQLExpr> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//                this.acceptChild(visitor, items);
//            }
        }

        @Override
        public SQLUpdateIndexesClause clone() {
            return null;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == name) {
                setName(target);
                return true;
            }
            return false;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
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
}
