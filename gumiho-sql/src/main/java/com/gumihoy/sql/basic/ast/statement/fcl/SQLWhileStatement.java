package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * [begin_label:] WHILE search_condition DO
 * statement_list
 * END WHILE [end_label]
 * https://dev.mysql.com/doc/refman/5.7/en/while.html
 * <p>
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLWhileStatement extends AbstractSQLStatement {

    protected ISQLName beginLabel;

    protected ISQLExpr condition;

    protected final List<ISQLObject> statements = new ArrayList<>();

    protected ISQLName endLabel;


    public SQLWhileStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, beginLabel);
            this.acceptChild(visitor, condition);
            this.acceptChild(visitor, statements);
            this.acceptChild(visitor, endLabel);
        }
    }

    @Override
    public SQLWhileStatement clone() {
        SQLWhileStatement x = new SQLWhileStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLWhileStatement x) {
        super.cloneTo(x);

        if (this.beginLabel != null) {
            ISQLName beginLabelClone = this.beginLabel.clone();
            x.setBeginLabel(beginLabelClone);
        }

        ISQLExpr exprClone = this.condition.clone();
        x.setCondition(exprClone);

        for (ISQLObject stmt : statements) {
            ISQLObject stmtClone = stmt.clone();
            x.addStatement(stmtClone);
        }

        if (this.endLabel != null) {
            ISQLName endLabelClone = this.endLabel.clone();
            x.setEndLabel(endLabelClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == beginLabel
                && target instanceof ISQLName) {
            setBeginLabel((ISQLName) target);
            return true;
        }

        if (source == condition) {
            setCondition(target);
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
        return null;// SQLObjectType.WHILE;
    }


    public ISQLName getBeginLabel() {
        return beginLabel;
    }

    public void setBeginLabel(ISQLName beginLabel) {
        setChildParent(beginLabel);
        this.beginLabel = beginLabel;
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

    public ISQLName getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(ISQLName endLabel) {
        setChildParent(endLabel);
        this.endLabel = endLabel;
    }
}
