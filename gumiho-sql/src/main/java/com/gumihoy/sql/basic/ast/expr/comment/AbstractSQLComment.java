package com.gumihoy.sql.basic.ast.expr.comment;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;

/**
 * @author kent on 2019-07-18.
 */
public abstract class AbstractSQLComment extends AbstractSQLExpr implements ISQLComment {

    protected String comment;

    public AbstractSQLComment() {
    }

    public AbstractSQLComment(String comment) {
        this.comment = comment;
    }

    @Override
    public AbstractSQLComment clone() {
        throw new UnsupportedOperationException();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
