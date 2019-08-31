package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.ISQLConstraintState;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * USING INDEX   [physical_attributes_clause| TABLESPACE tablespace]...
 * |
 * USING NO INDEX
 * <p>
 * using_index_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent on 2018/6/23.
 */
public interface ISQLUsingIndexClause extends ISQLExpr {
    @Override
    ISQLUsingIndexClause clone();


    /**
     * using_index_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
     * <p>
     * USING INDEX   [physical_attributes_clause| TABLESPACE tablespace]...
     * |
     * USING NO INDEX
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
     *
     * @author kent on 2018/6/23.
     */
    class SQLUsingIndexClause extends AbstractSQLExpr implements ISQLUsingIndexClause, ISQLConstraintState {

        protected final List<ISQLExpr> items = new ArrayList<>();

        public SQLUsingIndexClause(ISQLExpr ... items) {
            for (ISQLExpr item : items) {
                addItem(item);
            }
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, items);
            }
        }

        @Override
        public SQLUsingIndexClause clone() {
            SQLUsingIndexClause x = new SQLUsingIndexClause();

            for (ISQLExpr item : items) {
                ISQLExpr itemClone = item.clone();
                x.addItem(itemClone);
            }
            return x;
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

    /**
     * ( create_index_statement )
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
     */
    public static class SQLCreateIndexStatementItem extends AbstractSQLExpr {

        protected SQLCreateIndexStatement createIndexStatement;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, createIndexStatement);
            }
        }

        @Override
        public SQLCreateIndexStatementItem clone() {
            SQLCreateIndexStatementItem x = new SQLCreateIndexStatementItem();
            SQLCreateIndexStatement createIndexStatementClone = this.createIndexStatement.clone();
            x.setCreateIndexStatement(createIndexStatementClone);
            return x;
        }

        public SQLCreateIndexStatement getCreateIndexStatement() {
            return createIndexStatement;
        }

        public void setCreateIndexStatement(SQLCreateIndexStatement createIndexStatement) {
            setChildParent(createIndexStatement);
            this.createIndexStatement = createIndexStatement;
        }
    }


    /**
     * USING INDEX   [physical_attributes_clause| TABLESPACE tablespace]...
     * |
     * USING NO INDEX
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
     *
     * @author kent on 2018/6/23.
     */
    class SQLUsingNoIndexClause extends AbstractSQLExpr implements ISQLUsingIndexClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLUsingNoIndexClause clone() {
            SQLUsingNoIndexClause x = new SQLUsingNoIndexClause();
            return x;
        }

    }


}
