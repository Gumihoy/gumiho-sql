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
 * [begin_label:] REPEAT statement_list UNTIL search_condition END REPEAT [end_label]
 * https://dev.mysql.com/doc/refman/8.0/en/repeat.html
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLRepeatStatement extends AbstractSQLStatement {

    protected ISQLName beginLabel;
    protected final List<ISQLObject> statements = new ArrayList<>();
    protected ISQLExpr condition;
    protected ISQLName endLabel;


    public SQLRepeatStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, beginLabel);
            this.acceptChild(visitor, statements);
            this.acceptChild(visitor, condition);
            this.acceptChild(visitor, endLabel);
        }
    }

    @Override
    public SQLRepeatStatement clone() {
        SQLRepeatStatement x = new SQLRepeatStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLRepeatStatement x) {
        super.cloneTo(x);

        if (this.beginLabel != null) {
            ISQLName beginLabelClone = this.beginLabel.clone();
            x.setBeginLabel(beginLabelClone);
        }
        for (ISQLObject statement : this.statements) {
            ISQLObject statementClone = statement.clone();
            x.addStatement(statementClone);
        }

        if (this.condition != null) {
            ISQLExpr conditionClone = this.condition.clone();
            x.setCondition(conditionClone);
        }

        if (this.endLabel != null) {
            ISQLName endLabelClone = this.endLabel.clone();
            x.setEndLabel(endLabelClone);
        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.REPEAT;
    }


    public ISQLName getBeginLabel() {
        return beginLabel;
    }

    public SQLRepeatStatement setBeginLabel(ISQLName beginLabel) {
        setChildParent(beginLabel);
        this.beginLabel = beginLabel;
        return this;
    }

    public List<ISQLObject> getStatements() {
        return statements;
    }

    public SQLRepeatStatement addStatement(ISQLObject statement) {
        if (statement == null) {
            return this;
        }
        setChildParent(statement);
        this.statements.add(statement);
        return this;
    }

    public ISQLExpr getCondition() {
        return condition;
    }

    public SQLRepeatStatement setCondition(ISQLExpr condition) {
        setChildParent(condition);
        this.condition = condition;
        return this;
    }

    public ISQLName getEndLabel() {
        return endLabel;
    }

    public SQLRepeatStatement setEndLabel(ISQLName endLabel) {
        setChildParent(endLabel);
        this.endLabel = endLabel;
        return this;
    }
}
