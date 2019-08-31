package com.gumihoy.sql.basic.ast.expr.table.element.constraint.option;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * EXCEPTIONS INTO [ schema. ] table
 * <p>
 * https://docs.oracle.com/cd/B28359_01/server.111/b28286/clauses002.htm#CJAFFBAA
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent on 2018/6/26.
 */
public class SQLExceptionsClause extends AbstractSQLExpr implements ISQLConstraintState {

    protected ISQLName name;

    public SQLExceptionsClause(ISQLName name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLExceptionsClause clone() {
        ISQLName nameClone = this.name.clone();
        SQLExceptionsClause x = new SQLExceptionsClause(nameClone);

        return x;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }
}
