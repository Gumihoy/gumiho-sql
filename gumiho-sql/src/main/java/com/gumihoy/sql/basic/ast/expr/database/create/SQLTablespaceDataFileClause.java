package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSizeClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * DATAFILES { SIZE size_clause | autoextend_clause }...
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/tablespace_datafile_clauses.html
 *
 * @author kent on 2019-07-21.
 */
public class SQLTablespaceDataFileClause extends AbstractSQLExpr {

    protected final List<ISQLExpr> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public AbstractSQLExpr clone() {
        return super.clone();
    }

    public List<ISQLExpr> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        items.add(item);
    }


    public static class SQLSizeSizeClauseItem extends AbstractSQLExpr {
        protected SQLSizeClause sizeClause;

        public SQLSizeSizeClauseItem(SQLSizeClause sizeClause) {
            setSizeClause(sizeClause);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, sizeClause);
            }
        }

        public SQLSizeClause getSizeClause() {
            return sizeClause;
        }

        public void setSizeClause(SQLSizeClause sizeClause) {
            setChildParent(sizeClause);
            this.sizeClause = sizeClause;
        }
    }

}
