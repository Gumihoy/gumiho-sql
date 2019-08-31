package com.gumihoy.sql.basic.ast.expr.view.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SET SCHEMA new_schema
 * https://www.postgresql.org/docs/10/static/sql-alterview.html
 *
 * @author kent on 2018/7/13.
 */
public class SQLAlterViewSetSchemalAction extends AbstractSQLExpr implements ISQLAlterViewAction {

    protected ISQLName name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterViewSetSchemalAction clone() {
        SQLAlterViewSetSchemalAction x = new SQLAlterViewSetSchemalAction();
        return x;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }
}
