package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

/**
 * owner::name()
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#static%20method%20invocation
 *
 * @author kent on 2018/4/28.
 */
public class SQLStaticMethodInvocation extends AbstractSQLFunction {

    protected ISQLName owner;

    public SQLStaticMethodInvocation(String owner, String name) {
        super(name);
        setOwner(SQLUtils.ofName(owner));
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, owner);
//            this.acceptChild(visitor, nameExpr);
//            this.acceptChild(visitor, arguments);
//        }
    }


    public ISQLName getOwner() {
        return owner;
    }

    public void setOwner(ISQLName owner) {
        setChildParent(owner);
        this.owner = owner;
    }
}
