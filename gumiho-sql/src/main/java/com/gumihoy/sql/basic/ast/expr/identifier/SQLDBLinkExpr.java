package com.gumihoy.sql.basic.ast.expr.identifier;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

/**
 * name@dbLink
 * <p>
 * 'name'@'dblink'
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLDBLinkExpr extends AbstractSQLExpr implements ISQLName {

    protected ISQLExpr name;
    protected ISQLExpr dbLink;

    protected String fullName;

    public SQLDBLinkExpr(String name, String dbLink) {
        setName(SQLUtils.ofName(name));
        setDbLink(SQLUtils.ofName(dbLink));
    }

    public SQLDBLinkExpr(ISQLExpr name, ISQLExpr dbLink) {
        setName(name);
        setDbLink(dbLink);
    }

    public static SQLDBLinkExpr of(ISQLName name, ISQLName dbLink) {
        return new SQLDBLinkExpr(name, dbLink);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, dbLink);
        }
    }

    @Override
    public SQLDBLinkExpr clone() {
        ISQLExpr nameClone = this.name.clone();
        ISQLExpr dbLinkClone = this.dbLink.clone();

        SQLDBLinkExpr x = new SQLDBLinkExpr(nameClone, dbLinkClone);
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        boolean replace = super.replace(source, target);
        if (replace) {
            return true;
        }

        if (source == dbLink
                && target instanceof ISQLIdentifier) {
            setDbLink((ISQLIdentifier) target);
            return true;
        }
        return false;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLExpr getDbLink() {
        return dbLink;
    }

    public void setDbLink(ISQLExpr dbLink) {
        if (dbLink == null) {
            throw new IllegalArgumentException("dbLink is null");
        }
        setChildParent(dbLink);

        this.fullName = null;
        this.dbLink = dbLink;
    }


    @Override
    public String getSimpleName() {
        return null;
    }

    @Override
    public String getFullName() {
        if (fullName == null) {
            fullName = name.toString() + "@" + dbLink.toString();
        }
        return this.fullName;
    }

    public long hash() {
        return 0;
    }

    public long lowerHash() {
        return 0;
    }

    public long fullNameHash() {
        return 0;
    }

    public long fullNameLowerHash() {
        return 0;
    }

}
