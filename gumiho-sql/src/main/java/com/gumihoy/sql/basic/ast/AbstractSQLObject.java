package com.gumihoy.sql.basic.ast;

import com.gumihoy.sql.basic.ast.expr.comment.ISQLComment;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.basic.visitor.SQLASTOutputVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractSQLObject implements ISQLObject {

    protected DBType dbType;
    protected DBType targetDBType;
    protected ISQLObject parent;
    protected boolean afterSemi = false;

    protected final List<ISQLComment> beforeComments = new ArrayList<>();
    protected final List<ISQLComment> afterComments = new ArrayList<>();

    public AbstractSQLObject() {
    }

    public AbstractSQLObject(DBType dbType) {
        this.dbType = dbType;
    }

    @Override
    public final void accept(ISQLASTVisitor visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException();
        }

        visitor.preVisit(this);

        accept0(visitor);

        visitor.postVisit(this);
    }

    protected abstract void accept0(ISQLASTVisitor visitor);

    protected final void acceptChild(ISQLASTVisitor visitor, Collection<? extends ISQLObject> children) {
        if (children == null) {
            return;
        }

        for (Iterator<? extends ISQLObject> iterator = children.iterator(); iterator.hasNext(); ) {
            acceptChild(visitor, iterator.next());
        }
    }

    protected final void acceptChild(ISQLASTVisitor visitor, ISQLObject child) {
        if (child == null) {
            return;
        }

        child.accept(visitor);
    }

    @Override
    public ISQLObject clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public void cloneTo(ISQLObject x) {
        x.setAfterSemi(this.afterSemi);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        output(out);
        return out.toString();
    }

    public void output(StringBuilder out) {
        SQLASTOutputVisitor outputVisitor = SQLUtils.createASTOutputVisitor(out, getDbType(), getTargetDBType());
        this.accept(outputVisitor);
    }

    @Override
    public DBType getDbType() {
        if (dbType != null) {
            return dbType;
        }

        ISQLObject parent = this.parent;
        for (; ; ) {
            if (parent == null) {
                return null;
            }

            if (parent.getDbType() != null) {
                this.dbType = parent.getDbType();
                return this.dbType;
            }
            parent = parent.getParent();
        }

    }

    @Override
    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }


    @Override
    public DBType getTargetDBType() {
        if (targetDBType != null) {
            return targetDBType;
        }

        ISQLObject parent = this.parent;
        for (; ; ) {
            if (parent == null) {
                return null;
            }

            if (parent.getTargetDBType() != null) {
                this.targetDBType = parent.getTargetDBType();
                return this.targetDBType;
            }
            parent = parent.getParent();
        }
    }

    @Override
    public void setTargetDBType(DBType targetDBType) {
        this.targetDBType = targetDBType;
    }

    @Override
    public ISQLObject getParent() {
        return parent;
    }

    @Override
    public void setParent(ISQLObject parent) {
        this.parent = parent;
    }

    public void setChildParent(ISQLObject child) {
        if (child == null) {
            return;
        }
        child.setParent(this);
        setChildDBType(child);
    }

    private void setChildDBType(ISQLObject child) {
        if (child == null) {
            return;
        }
        child.setDbType(this.getDbType());
    }

    @Override
    public boolean isAfterSemi() {
        return afterSemi;
    }

    @Override
    public void setAfterSemi(boolean afterSemi) {
        this.afterSemi = afterSemi;
    }

    @Override
    public List<ISQLComment> getBeforeComments() {
        return beforeComments;
    }

    @Override
    public void addBeforeComment(ISQLComment comment) {
        beforeComments.add(comment);
    }

    @Override
    public void addBeforeComment(List<ISQLComment> comments) {
        beforeComments.addAll(comments);
    }

    @Override
    public List<ISQLComment> getAfterComments() {
        return afterComments;
    }

    @Override
    public void addAfterComment(ISQLComment comment) {
        if (comment == null) {
            return;
        }
        afterComments.add(comment);
    }

    @Override
    public void addAfterComment(List<ISQLComment> comments) {
        if (comments == null) {
            return;
        }
        afterComments.addAll(comments);
    }

    @Override
    public boolean hasBeforeComment() {
        return beforeComments.size() > 0;
    }

    @Override
    public boolean hasAfterComment() {
        return afterComments.size() > 0;
    }
}
