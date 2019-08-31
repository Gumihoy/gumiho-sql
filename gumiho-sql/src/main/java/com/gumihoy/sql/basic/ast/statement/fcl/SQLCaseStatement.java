package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLLabelStatement;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CASE [selector]
 * WHEN boolean_expression THEN statement ;
 * [ WHEN boolean_expression THEN statement ; ]...
 * [ ELSE statement [ statement ]... ;
 * END CASE
 * https://dev.mysql.com/doc/refman/5.7/en/case.html
 * <p>
 * <p>
 * CASE expr? caseStatementWhenItem+  caseStatementElseClause? END CASE nameIdentifier?
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/CASE-statement.html#GUID-F4251A23-0284-4990-A156-00A92F83BC35
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CASE-statement.html#GUID-F4251A23-0284-4990-A156-00A92F83BC35
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLCaseStatement extends AbstractSQLStatement {

    protected ISQLExpr selector;

    protected final List<SQLCaseStatementWhenItem> whenItems = new ArrayList<>();

    protected SQLCaseStatementElseClause elseClause;

    protected ISQLName endLabel;

    public SQLCaseStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, selector);
            this.acceptChild(visitor, whenItems);
            this.acceptChild(visitor, elseClause);
        }
    }

    @Override
    public SQLCaseStatement clone() {
        SQLCaseStatement x = new SQLCaseStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLCaseStatement x) {
        super.cloneTo(x);

        ISQLExpr selectorClone = this.selector.clone();
        x.setSelector(selectorClone);

        for (SQLCaseStatementWhenItem whenItem : whenItems) {
            SQLCaseStatementWhenItem whenItemClone = whenItem.clone();
            x.addWhenItem(whenItem);
        }

        if (elseClause != null) {
            SQLCaseStatementElseClause elseClauseClone = elseClause.clone();
            x.setElseClause(elseClauseClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == selector) {
            setSelector(target);
            return true;
        }

        if (source == endLabel
                && target instanceof ISQLName) {
            setEndLabel((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.CASE;
    }

    public ISQLExpr getSelector() {
        return selector;
    }

    public void setSelector(ISQLExpr selector) {
        setChildParent(selector);
        this.selector = selector;
    }


    public List<SQLCaseStatementWhenItem> getWhenItems() {
        return whenItems;
    }

    public void addWhenItem(SQLCaseStatementWhenItem whenItem) {
        if (whenItem == null) {
            return;
        }
        setChildParent(whenItem);
        this.whenItems.add(whenItem);
    }

    public SQLCaseStatementElseClause getElseClause() {
        return elseClause;
    }

    public void setElseClause(SQLCaseStatementElseClause elseClause) {
        setChildParent(elseClause);
        this.elseClause = elseClause;
    }


    public ISQLName getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(ISQLName endLabel) {
        setChildParent(endLabel);
        this.endLabel = endLabel;
    }


    /**
     * when expr then statement
     */
    public static class SQLCaseStatementWhenItem extends AbstractSQLExpr {

        protected ISQLExpr expr;

        protected ISQLObject statement;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
                this.acceptChild(visitor, statement);
            }
        }

        @Override
        public SQLCaseStatementWhenItem clone() {
            SQLCaseStatementWhenItem x = new SQLCaseStatementWhenItem();

            ISQLExpr exprClone = this.expr.clone();
            x.setExpr(exprClone);

            ISQLObject statementClone = this.statement.clone();
            x.setStatement(statementClone);

            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == expr) {
                setExpr(target);
                return true;
            }
            return false;
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }

        public ISQLObject getStatement() {
            return statement;
        }

        public void setStatement(ISQLObject statement) {
            setChildParent(statement);
            this.statement = statement;
        }
    }

    /**
     * else statement... ;
     */
    public static class SQLCaseStatementElseClause extends AbstractSQLExpr {

        protected final List<SQLLabelStatement> statements = new ArrayList<>();

        public SQLCaseStatementElseClause() {
            setAfterSemi(true);
        }

        public SQLCaseStatementElseClause(SQLLabelStatement... stmts) {
            for (SQLLabelStatement stmt : stmts) {
                this.addStatement(stmt);
            }
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, statements);
            }
        }

        @Override
        public SQLCaseStatementElseClause clone() {
            SQLCaseStatementElseClause x = new SQLCaseStatementElseClause();
            this.cloneTo(x);

            for (SQLLabelStatement statement : statements) {
                SQLLabelStatement statementClone = statement.clone();
                x.addStatement(statementClone);
            }
            return x;
        }


        public List<SQLLabelStatement> getStatements() {
            return statements;
        }

        public void addStatement(SQLLabelStatement statement) {
            if (statement == null) {
                return;
            }
            setChildParent(statement);
            this.statements.add(statement);
        }
    }


}
