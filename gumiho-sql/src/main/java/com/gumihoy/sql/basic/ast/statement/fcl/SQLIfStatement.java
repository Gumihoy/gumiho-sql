package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * IF search_condition THEN statement_list
 * [ELSEIF search_condition THEN statement_list] ...
 * [ELSE statement_list]
 * END IF
 * https://dev.mysql.com/doc/refman/5.7/en/if.html
 * <p>
 * <p>
 * IF boolean_expression THEN statement [ statement ]...
 * [ ELSIF boolean_expression THEN statement [ statement ]... ]...
 * [ ELSE statement [ statement ]... ]
 * END IF ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/IF-statement.html
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLIfStatement extends AbstractSQLStatement {

    protected ISQLExpr condition;

    protected final List<ISQLObject> statements = new ArrayList<>();

    protected final List<SQLElseIf> elseIfs = new ArrayList<>();

    protected final List<ISQLObject> elseStatements = new ArrayList<>();


    public SQLIfStatement(DBType dbType) {
        super(dbType);
    }

    public SQLIfStatement(DBType dbType, ISQLExpr condition, ISQLStatement... stmts) {
        super(dbType);
        setCondition(condition);
        for (ISQLStatement stmt : stmts) {
            this.addStatement(stmt);
        }
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, condition);
            this.acceptChild(visitor, statements);
            this.acceptChild(visitor, elseIfs);
            this.acceptChild(visitor, elseStatements);
        }
    }

    @Override
    public SQLIfStatement clone() {
        SQLIfStatement x = new SQLIfStatement(this.dbType);

        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLIfStatement x) {
        super.cloneTo(x);

        ISQLExpr conditionClone = this.condition.clone();
        x.setCondition(conditionClone);

        for (ISQLObject i : statements) {
            ISQLObject clone = i.clone();
            x.addStatement(clone);
        }

        for (SQLElseIf i : elseIfs) {
            SQLElseIf clone = i.clone();
            x.addElseIf(clone);
        }

        for (ISQLObject i : elseStatements) {
            ISQLObject clone = i.clone();
            x.addElseStatement(clone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == condition) {
            setCondition(target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.IF;
    }



    public ISQLExpr getCondition() {
        return condition;
    }

    public void setCondition(ISQLExpr condition) {
        setChildParent(condition);
        this.condition = condition;
    }

    public List<ISQLObject> getStatements() {
        return statements;
    }

    public void addStatement(ISQLObject stmt) {
        if (stmt == null) {
            return;
        }
        setChildParent(stmt);
        this.statements.add(stmt);
    }

    public List<SQLElseIf> getElseIfs() {
        return elseIfs;
    }

    public void addElseIf(SQLElseIf elseIf) {
        if (elseIf == null) {
            return;
        }
        setChildParent(elseIf);
        this.elseIfs.add(elseIf);
    }

    public List<ISQLObject> getElseStatements() {
        return elseStatements;
    }

    public void addElseStatement(ISQLObject stmt) {
        if (stmt == null) {
            return;
        }
        setChildParent(stmt);
        this.elseStatements.add(stmt);
    }

    /**
     * ELSIF boolean_expression THEN statement [ statement ]...
     */
    public static class SQLElseIf extends AbstractSQLExpr {

        protected ISQLExpr condition;

        protected final List<ISQLObject> statements = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, condition);
//                this.acceptChild(visitor, statements);
//            }
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == condition) {
                setCondition(target);
                return true;
            }
            return false;
        }

        @Override
        public SQLElseIf clone() {
            SQLElseIf x = new SQLElseIf();
            this.cloneTo(x);
            return x;
        }


        public void cloneTo(SQLElseIf x) {
            super.cloneTo(x);

            ISQLExpr conditionClone = this.condition.clone();
            x.setCondition(conditionClone);

            for (ISQLObject i : statements) {
                ISQLObject clone = i.clone();
                x.addStatement(clone);
            }
        }

        public ISQLExpr getCondition() {
            return condition;
        }

        public void setCondition(ISQLExpr condition) {
            setChildParent(condition);
            this.condition = condition;
        }

        public List<ISQLObject> getStatements() {
            return statements;
        }

        public void addStatement(ISQLObject stmt) {
            if (stmt == null) {
                return;
            }
            setChildParent(stmt);
            this.statements.add(stmt);
        }


    }


}
