package com.gumihoy.sql.basic.ast.expr.method.window;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * over windowName
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#window%20function
 *
 * @author kent on 2018/5/22.
 */
public class SQLOverWindowNameClause extends AbstractSQLExpr implements ISQLOverClause {

    protected ISQLName name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLOverWindowNameClause clone() {
        SQLOverWindowNameClause x = new SQLOverWindowNameClause();

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLOverWindowNameClause x) {
        super.cloneTo(x);

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }
}

