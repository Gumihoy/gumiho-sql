package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr as EVALNAME expr
 *
 * @author kent on 2018/5/28.
 */
public class SQLXmlEvalNameExprArgument extends AbstractSQLExpr {

    protected ISQLExpr value;

    protected ISQLExpr alias;

    public SQLXmlEvalNameExprArgument(ISQLExpr value, ISQLExpr alias) {
        setValue(value);
        setAlias(alias);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, value);
//            this.acceptChild(visitor, alias);
//        }
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        this.value = value;
    }

    public ISQLExpr getAlias() {
        return alias;
    }

    public void setAlias(ISQLExpr alias) {
        this.alias = alias;
    }
}
