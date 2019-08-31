package com.gumihoy.sql.basic.ast.expr.identifier;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.SQLObjectType;
import com.gumihoy.sql.util.SQLUtils;

/**
 * @author kent on 2019-06-15.
 */
public class SQLPropertyExpr extends AbstractSQLName {

    protected ISQLExpr owner;

    public SQLPropertyExpr(String owner, String name) {
        this(owner, SQLUtils.ofName(name));
    }

    public SQLPropertyExpr(String owner, ISQLIdentifier name) {
        this(SQLUtils.ofName(owner), name);
    }

    public SQLPropertyExpr(ISQLExpr owner, ISQLIdentifier name) {
        if (owner == null) {
            throw new IllegalArgumentException("owner is null.");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is null.");
        }
        setOwner(owner);
        setName(name);
    }

    public static SQLPropertyExpr of(String owner, ISQLIdentifier name) {
        return new SQLPropertyExpr(owner, name);
    }

    public static SQLPropertyExpr of(ISQLExpr owner, ISQLIdentifier name) {
        return new SQLPropertyExpr(owner, name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, owner);
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLPropertyExpr clone() {
        ISQLExpr ownerClone = this.owner.clone();
        ISQLIdentifier nameClone = this.name.clone();
        return new SQLPropertyExpr(ownerClone, nameClone);
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }


    public ISQLExpr getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        setOwner(SQLUtils.ofName(owner));
    }

    public void setOwner(ISQLExpr owner) {
        setChildParent(owner);
        this.owner = owner;
    }

}
