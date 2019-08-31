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
 * WHILE expr LOOP statement... END LOOP [ label ] ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/WHILE-LOOP-statement.html#GUID-9339C3AD-7F41-4D3F-9B2D-6FC5DCE44C6B
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/WHILE-LOOP-statement.html#GUID-9339C3AD-7F41-4D3F-9B2D-6FC5DCE44C6B
 *
 * @author kent on 2018/6/14.
 */
public class SQLWhileLoopStatement extends AbstractSQLStatement {

    protected ISQLExpr condition;

    protected final List<ISQLObject> statements = new ArrayList<>();

    protected ISQLName endLabel;

    public SQLWhileLoopStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, condition);
            this.acceptChild(visitor, statements);
            this.acceptChild(visitor, endLabel);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
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
        return null;// SQLObjectType.WHILE_LOOP;
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
