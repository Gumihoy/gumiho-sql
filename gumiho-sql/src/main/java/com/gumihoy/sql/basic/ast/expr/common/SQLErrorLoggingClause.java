package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * error_logging_clause
 * <p>
 * LOG ERRORS
 * [ INTO [schema.] table ]
 * [ (simple_expression) ]
 * [ REJECT LIMIT rejectLimit={ integer | UNLIMITED } ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/INSERT.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/MERGE.html
 *
 * @author kent onCondition 2018/4/8.
 */
public class SQLErrorLoggingClause extends AbstractSQLExpr {

    protected ISQLName into;

    protected ISQLExpr expr;

    protected ISQLExpr rejectLimit;


    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, into);
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, rejectLimit);
        }
    }


    @Override
    public SQLErrorLoggingClause clone() {
        SQLErrorLoggingClause x = new SQLErrorLoggingClause();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLErrorLoggingClause x) {
        super.cloneTo(x);
        ISQLName intoClone = this.into.clone();
        x.setInto(intoClone);

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);

        ISQLExpr rejectLimitClone = this.rejectLimit.clone();
        x.setRejectLimit(rejectLimitClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == into
                && target instanceof ISQLName) {
            setInto((ISQLName)target);
            return true;
        }

        if (source == expr) {
            setExpr(target);
            return true;
        }

        if (source == rejectLimit) {
            setRejectLimit(target);
            return true;
        }

        return false;
    }

    public ISQLName getInto() {
        return into;
    }

    public void setInto(ISQLName into) {
        setChildParent(into);
        this.into = into;
    }

    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }

    public ISQLExpr getRejectLimit() {
        return rejectLimit;
    }

    public void setRejectLimit(ISQLExpr rejectLimit) {
        setChildParent(rejectLimit);
        this.rejectLimit = rejectLimit;
    }
}
