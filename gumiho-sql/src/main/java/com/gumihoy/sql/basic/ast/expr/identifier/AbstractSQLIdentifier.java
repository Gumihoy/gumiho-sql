package com.gumihoy.sql.basic.ast.expr.identifier;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.util.HashUtils;

/**
 * @author kent on 2019-06-15.
 */
public abstract class AbstractSQLIdentifier extends AbstractSQLExpr implements ISQLIdentifier {

    protected String name;
    protected long nameHashCode64;
    protected long lowerNameHashCode64;

    public AbstractSQLIdentifier(String name) {
        this.name = name;
    }

    @Override
    public AbstractSQLIdentifier clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSimpleName() {
        return name;
    }

    @Override
    public String getFullName() {
        return name;
    }

    @Override
    public long hash() {
        if (this.nameHashCode64 == 0) {
            this.nameHashCode64 = HashUtils.fnv_1a_64(getSimpleName());
        }
        return nameHashCode64;
    }

    @Override
    public long fullNameHash() {
        if (this.nameHashCode64 == 0) {
            this.nameHashCode64 = HashUtils.fnv_1a_64(getFullName());
        }
        return nameHashCode64;
    }

    @Override
    public long lowerHash() {
        if (this.lowerNameHashCode64 == 0) {
            this.lowerNameHashCode64 = HashUtils.fnv_1a_64_lower(getSimpleName());
        }
        return lowerNameHashCode64;
    }

    @Override
    public long fullNameLowerHash() {
        if (this.lowerNameHashCode64 == 0) {
            this.lowerNameHashCode64 = HashUtils.fnv_1a_64_lower(getFullName());
        }
        return lowerNameHashCode64;
    }


}
