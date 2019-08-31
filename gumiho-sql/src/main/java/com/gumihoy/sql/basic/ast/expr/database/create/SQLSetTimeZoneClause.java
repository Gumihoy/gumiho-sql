package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SET TIME_ZONE = '{ { + | - } hh : mi | time_zone_region }'
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/set_time_zone_clause.html
 *
 * @author kent on 2019-07-21.
 */
public class SQLSetTimeZoneClause extends AbstractSQLExpr {

    protected ISQLExpr value;

    public SQLSetTimeZoneClause(ISQLExpr value) {
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLSetTimeZoneClause clone() {
        return null;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }
}
