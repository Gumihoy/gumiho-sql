package com.gumihoy.sql.basic.ast.expr.identifier;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.util.HashUtils;
import com.gumihoy.sql.util.SQLUtils;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractSQLName extends AbstractSQLExpr implements ISQLName {

    protected ISQLIdentifier name;

    protected String simpleName;
    protected String fullName;
    protected long simpleNameHashCode64;
    protected long fullNameHashCode64;
    protected long lowerSimpleNameHashCode64;
    protected long lowerFullNameHashCode64;

    public AbstractSQLName() {
    }

    public AbstractSQLName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        setName(SQLUtils.ofName(name));
    }

    public AbstractSQLName(ISQLIdentifier name) {
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        setName(name);
    }

    @Override
    public AbstractSQLName clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLName x) {
        super.cloneTo(x);

        ISQLIdentifier nameClone = this.name.clone();
        x.setName(nameClone);

        x.name = this.name;
        x.fullName = this.fullName;
        x.simpleNameHashCode64 = this.simpleNameHashCode64;
        x.fullNameHashCode64 = this.fullNameHashCode64;
        x.lowerSimpleNameHashCode64 = this.lowerSimpleNameHashCode64;
        x.lowerFullNameHashCode64 = this.lowerFullNameHashCode64;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
//            setName(target);
        }
        return false;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLIdentifier name) {
        setChildParent(name);
        clearName();
        this.name = name;
    }

    public void clearName() {
        this.name = null;
        this.fullName = null;
    }

    @Override
    public String getSimpleName() {
        if (simpleName == null
                && name instanceof ISQLName) {
            this.simpleName = ((ISQLName) name).getSimpleName();
        }
        return simpleName;
    }

    @Override
    public String getFullName() {
        if (fullName == null
                && name instanceof ISQLName) {
            this.fullName = ((ISQLName) name).getFullName();
        }
        return fullName;
    }

    @Override
    public long hash() {
        if (this.simpleNameHashCode64 == 0) {
            this.simpleNameHashCode64 = HashUtils.fnv_1a_64(getSimpleName());
        }
        return simpleNameHashCode64;
    }

    @Override
    public long fullNameHash() {
        if (this.fullNameHashCode64 == 0) {
            this.fullNameHashCode64 = HashUtils.fnv_1a_64(getFullName());
        }
        return fullNameHashCode64;
    }

    @Override
    public long lowerHash() {
        if (this.lowerSimpleNameHashCode64 == 0) {
            this.lowerSimpleNameHashCode64 = HashUtils.fnv_1a_64_lower(getSimpleName());
        }
        return lowerSimpleNameHashCode64;
    }

    @Override
    public long fullNameLowerHash() {
        if (this.fullNameHashCode64 == 0) {
            this.fullNameHashCode64 = HashUtils.fnv_1a_64_lower(getFullName());
        }
        return fullNameHashCode64;
    }

}
