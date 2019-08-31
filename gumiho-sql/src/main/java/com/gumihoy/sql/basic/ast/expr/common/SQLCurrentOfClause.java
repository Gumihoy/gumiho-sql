package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * CURRENT OF <cursor name>
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#delete%20statement:%20positioned
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#update%20statement:%20positioned
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#dynamic%20delete%20statement:%20positioned
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#dynamic%20update%20statement:%20positioned
 *
 * @author kent on 2018/5/9.
 */
public class SQLCurrentOfClause extends AbstractSQLExpr {

    protected ISQLName name;

    public SQLCurrentOfClause() {
    }

    public SQLCurrentOfClause(ISQLName name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLCurrentOfClause clone() {
        SQLCurrentOfClause x = new SQLCurrentOfClause();
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
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
