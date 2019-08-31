package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * name(+)
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Joins.html#GUID-29A4584C-0741-4E6A-A89B-DCFAA222994A
 *
 * @author kent on 2018/5/16.
 */
public class SQLOuterJoinExpr extends AbstractSQLExpr {

    protected ISQLExpr name;

    public SQLOuterJoinExpr(ISQLExpr name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }
}
