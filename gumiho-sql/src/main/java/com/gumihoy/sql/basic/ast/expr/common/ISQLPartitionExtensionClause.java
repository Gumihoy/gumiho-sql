package com.gumihoy.sql.basic.ast.expr.common;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * PARTITION ( expr[, expr ]... )
 * PARTITION FOR ( expr[, expr ]... )
 * SUBPARTITION (subpartition)
 * SUBPARTITION FOR  ( expr[, expr ]... )
 * <p>
 * partition_extension_clause
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Syntax-for-Schema-Objects-and-Parts-in-SQL-Statements.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/LOCK-TABLE.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html
 *
 * @author kent on 2018/6/29.
 */
public interface ISQLPartitionExtensionClause extends ISQLExpr {

    @Override
    ISQLPartitionExtensionClause clone();

    List<ISQLExpr> getItems();

    void addItem(ISQLExpr item);


    abstract class AbstractSQLPartitionExtensionClause extends AbstractSQLExpr implements ISQLPartitionExtensionClause {
        protected final List<ISQLExpr> items = new ArrayList<>();

        @Override
        public AbstractSQLPartitionExtensionClause clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }

        public void cloneTo(AbstractSQLPartitionExtensionClause x) {
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

        @Override
        public List<ISQLExpr> getItems() {
            return items;
        }

        @Override
        public void addItem(ISQLExpr item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.items.add(item);
        }
    }


    /**
     * PARTITION ( expr[, expr ]... )
     * <p>
     * https://dev.mysql.com/doc/refman/8.0/en/join.html
     *
     * @author kent on 2018/5/22.
     */
    class SQLPartitionClause extends AbstractSQLPartitionExtensionClause {

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
        }
    }


    /**
     * PARTITION FOR ( expr[, expr ]... )
     * <p>
     * https://dev.mysql.com/doc/refman/8.0/en/join.html
     *
     * @author kent on 2018/6/29.
     */
    class SQLPartitionForClause extends AbstractSQLPartitionExtensionClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, items);
            }
        }

        @Override
        public SQLPartitionForClause clone() {
            SQLPartitionForClause x = new SQLPartitionForClause();
            this.cloneTo(x);
            return x;
        }

        public void cloneTo(SQLPartitionClause x) {
            super.cloneTo(x);
        }
    }


    /**
     * SUBPARTITION ( expr[, expr ]... )
     * <p>
     * https://dev.mysql.com/doc/refman/8.0/en/join.html
     *
     * @author kent on 2018/5/22.
     */
    class SQLSubPartitionClause extends AbstractSQLPartitionExtensionClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, items);
            }
        }

        @Override
        public SQLSubPartitionClause clone() {
            SQLSubPartitionClause x = new SQLSubPartitionClause();
            this.cloneTo(x);
            return x;
        }

        public void cloneTo(SQLPartitionClause x) {
            super.cloneTo(x);
        }
    }


    /**
     * SUBPARTITION FOR ( expr[, expr ]... )
     * <p>
     * https://dev.mysql.com/doc/refman/8.0/en/join.html
     *
     * @author kent on 2018/5/22.
     */
    class SQLSubPartitionForClause extends AbstractSQLPartitionExtensionClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, items);
            }
        }

        @Override
        public SQLSubPartitionForClause clone() {
            SQLSubPartitionForClause x = new SQLSubPartitionForClause();
            this.cloneTo(x);
            return x;
        }

        public void cloneTo(SQLPartitionClause x) {
            super.cloneTo(x);
        }
    }

}
