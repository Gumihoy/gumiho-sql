package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * TABLESPACE tablespace_set
 * TABLESPACE SET tablespace_set
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/segment_attributes_clause.html
 *
 * @author kent on 2019-07-16.
 */
public class SQLTableSpaceSetClause extends AbstractSQLExpr implements ISQLLobStorageParameter, ISQLSegmentAttribute {

    protected boolean set;
    protected ISQLExpr name;

    public SQLTableSpaceSetClause(ISQLExpr name) {
        this(false, name);
    }

    public SQLTableSpaceSetClause(boolean set, ISQLExpr name) {
        this.set = set;
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLTableSpaceSetClause clone() {
        return null;
    }

    public boolean isSet() {
        return set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }
}
