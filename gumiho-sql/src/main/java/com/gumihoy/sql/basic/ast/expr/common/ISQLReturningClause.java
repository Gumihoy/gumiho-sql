package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * { RETURN | RETURNING } expr [, expr ]... INTO data_item [, data_item ]...
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/returning_clause.html
 *
 * @author kent on 2018/6/5.
 */
public interface ISQLReturningClause extends ISQLExpr {
    @Override
    ISQLReturningClause clone();

    List<ISQLExpr> getReturningItems();
    void addReturningItem(ISQLExpr returningItem);

    boolean isBulkCollect();
    void setBulkCollect(boolean bulkCollect);

    List<ISQLExpr> getIntoItems();
    void addIntoItem(ISQLExpr intoItem);

    abstract class AbstractSQLReturningClause extends AbstractSQLExpr implements ISQLReturningClause {

        protected final List<ISQLExpr> returningItems = new ArrayList<>();
        protected boolean bulkCollect;
        protected final List<ISQLExpr> intoItems = new ArrayList<>();

        @Override
        public AbstractSQLReturningClause clone() {
            throw new UnsupportedOperationException();
        }

        public void cloneTo(AbstractSQLReturningClause x) {
            super.cloneTo(x);

        }

        public List<ISQLExpr> getReturningItems() {
            return returningItems;
        }

        public void addReturningItem(ISQLExpr returningItem) {
            if (returningItem == null) {
                return;
            }
            setChildParent(returningItem);
            this.returningItems.add(returningItem);
        }

        public boolean isBulkCollect() {
            return bulkCollect;
        }

        public void setBulkCollect(boolean bulkCollect) {
            this.bulkCollect = bulkCollect;
        }

        public List<ISQLExpr> getIntoItems() {
            return intoItems;
        }

        public void addIntoItem(ISQLExpr intoItem) {
            if (intoItem == null) {
                return;
            }
            setChildParent(intoItem);
            this.intoItems.add(intoItem);
        }
    }


    /**
     * RETURN expr [, expr ]... INTO data_item [, data_item ]...
     * RETURN expr [, expr ]... BULK COLLECT INTO data_item [, data_item ]...
     * <p>
     * RETURN INTO data_item [, data_item ]...
     * RETURN BULK COLLECT INTO data_item [, data_item ]...
     */
    class SQLReturnIntoClause extends AbstractSQLReturningClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, returningItems);
                this.acceptChild(visitor, intoItems);
            }
        }

        @Override
        public SQLReturningIntoClause clone() {
            SQLReturningIntoClause x = new SQLReturningIntoClause();
            this.cloneTo(x);
            return x;
        }


        public void cloneTo(SQLReturningIntoClause x) {
//            super.cloneTo(x);

//            x.returning = this.returning;
//            for (ISQLExpr returningItem : returningItems) {
//                ISQLExpr returningItemClone = returningItem.clone();
//                x.addReturningItem(returningItemClone);
//            }
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            boolean replace = replaceInList(returningItems, source, target, this);
            if (replace) {
                return true;
            }
            return false;
        }
    }

    /**
     * RETURNING expr [, expr ]... INTO data_item [, data_item ]...
     * RETURNING  expr [, expr ]... BULK COLLECT INTO data_item [, data_item ]...
     * <p>
     * <p>
     * RETURNING INTO data_item [, data_item ]...
     * RETURNING BULK COLLECT INTO data_item [, data_item ]...
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/RETURNING-INTO-clause.html
     *
     * @author kent on 2018/6/5.
     */
    class SQLReturningIntoClause extends AbstractSQLReturningClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, returningItems);
                this.acceptChild(visitor, intoItems);
            }
        }

        @Override
        public SQLReturningIntoClause clone() {
            SQLReturningIntoClause x = new SQLReturningIntoClause();
            this.cloneTo(x);
            return x;
        }


        public void cloneTo(SQLReturningIntoClause x) {
//            super.cloneTo(x);

//            x.returning = this.returning;
//            for (ISQLExpr returningItem : returningItems) {
//                ISQLExpr returningItemClone = returningItem.clone();
//                x.addReturningItem(returningItemClone);
//            }
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            boolean replace = replaceInList(returningItems, source, target, this);
            if (replace) {
                return true;
            }
            return false;
        }
    }


}
