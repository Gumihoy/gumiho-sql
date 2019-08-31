package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * procedure [ ( [ parameter [, parameter ]... ] ) ] ;
 * <p>
 * procedure_call
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D
 *
 * @author kent on 2018/6/14.
 */
public class SQLProcedureInvocation extends AbstractSQLExpr implements ISQLStatement {

    protected ISQLName name;
    protected boolean paren;
    protected final List<ISQLExpr> arguments = new ArrayList<>();


    public SQLProcedureInvocation(String name) {
        setName(SQLUtils.ofName(name));
    }

    public SQLProcedureInvocation(ISQLName name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, arguments);
        }
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public boolean isParen() {
        return paren;
    }

    public void setParen(boolean paren) {
        this.paren = paren;
    }

    public List<ISQLExpr> getArguments() {
        return arguments;
    }

    public void addArgument(ISQLExpr argument) {
        if (argument == null) {
            return;
        }
        if (!this.paren) {
            this.paren = true;
        }
        argument.setParent(this);
        this.arguments.add(argument);
    }

}
