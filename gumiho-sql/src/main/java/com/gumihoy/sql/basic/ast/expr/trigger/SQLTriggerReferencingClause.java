package com.gumihoy.sql.basic.ast.expr.trigger;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * NEW/OLD [AS] name
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#trigger%20definition
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TRIGGER-statement.html#GUID-AF9E33F1-64D1-4382-A6A4-EC33C36F237B
 *
 * @author kent on 2018/4/26.
 */
public class SQLTriggerReferencingClause extends AbstractSQLExpr {

    protected final List<ISQLItem> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLTriggerReferencingClause clone() {
        return null;
    }

    public List<ISQLItem> getItems() {
        return items;
    }

    public void addItem(ISQLItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        items.add(item);
    }


    public static interface ISQLItem extends ISQLExpr {
        @Override
        ISQLItem clone();

        boolean isAs();

        void setAs(boolean as);

        ISQLName getName();

        void setName(ISQLName name);
    }

    public static abstract class AbstractSQLItem extends AbstractSQLExpr implements ISQLItem {
        protected boolean as;

        protected ISQLName name;

        public AbstractSQLItem() {
        }

        public AbstractSQLItem(String name) {
            setName(SQLUtils.ofName(name));
        }

        public AbstractSQLItem(ISQLName name) {
            setName(name);
        }

        @Override
        public AbstractSQLItem clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }

        public void cloneTo(AbstractSQLItem x) {
            super.cloneTo(x);

            x.as = this.as;

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

        public boolean isAs() {
            return as;
        }

        public void setAs(boolean as) {
            this.as = as;
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }
    }


    public static class SQLOldItem extends AbstractSQLItem {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }
    }

    public static class SQLNewItem extends AbstractSQLItem {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }
    }

    public static class SQLParentItem extends AbstractSQLItem {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }
    }
}
