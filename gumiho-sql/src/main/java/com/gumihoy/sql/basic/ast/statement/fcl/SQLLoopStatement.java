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
 * [begin_label:] LOOP statement_list END LOOP [end_label]
 * https://dev.mysql.com/doc/refman/5.7/en/loop.html
 * <p>
 * LOOP statement... END LOOP [ label ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/basic-LOOP-statement.html#GUID-99AC48AC-D868-43C4-9E4D-6A7671942A39
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLLoopStatement extends AbstractSQLStatement {

    protected ISQLName beginLabel;

    protected final List<ISQLObject> statements = new ArrayList<>();

    protected ISQLName endLabel;

    public SQLLoopStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, beginLabel);
            this.acceptChild(visitor, statements);
            this.acceptChild(visitor, endLabel);
        }
    }

    @Override
    public SQLLoopStatement clone() {
        SQLLoopStatement x = new SQLLoopStatement(this.dbType);
        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        if (source == beginLabel
                && target instanceof ISQLName) {
            setBeginLabel((ISQLName) target);
        }

        if (source == endLabel
                && target instanceof ISQLName) {
            setEndLabel((ISQLName) target);
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.LOOP;
    }


    public ISQLName getBeginLabel() {
        return beginLabel;
    }

    public void setBeginLabel(ISQLName beginLabel) {
        setChildParent(beginLabel);
        this.beginLabel = beginLabel;
    }

    public List<ISQLObject> getStatements() {
        return statements;
    }

    public void addStatement(ISQLObject statement) {
        if (statement == null) {
            return;
        }
        setChildParent(statement);
        this.statements.add(statement);
    }

    public ISQLName getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(ISQLName endLabel) {
        setChildParent(endLabel);
        this.endLabel = endLabel;
    }
}
