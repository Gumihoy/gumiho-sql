package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SHRINK SPACE [ COMPACT ] [ CASCADE ]
 * <p>
 * shrink_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2018/7/10.
 */
public class SQLShrinkClause extends AbstractSQLExpr {

    protected boolean compact;
    protected boolean cascade;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLShrinkClause clone() {
        SQLShrinkClause x = new SQLShrinkClause();
        x.compact = this.compact;
        x.cascade = this.cascade;
        return x;
    }

    public boolean isCompact() {
        return compact;
    }

    public void setCompact(boolean compact) {
        this.compact = compact;
    }

    public boolean isCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }
}
